package com.buavto.services;

import com.buavto.AutoSite;
import com.buavto.builders.url.AbstractUrlBuilder;
import com.buavto.builders.url.UrlBuildersFactory;
import com.buavto.dao.ArticlesDao;
import com.buavto.model.Article;
import com.buavto.model.SearchRequest;
import com.buavto.strategies.AbstractArticlesParsingStrategy;
import com.buavto.strategies.ArticlesParsingStrategyFactory;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

@Component
public class ArticlesService {

    private final static String PATH_TO_HTMLS = ".\\target\\htmls\\";
    private final static Logger LOGGER = LogManager.getLogger(ArticlesService.class.getName());

    @Autowired
    private ArticlesDao articlesDao;
    @Autowired
    private UrlBuildersFactory urlBuildersFactory;
    @Autowired
    private HtmlUrlReader htmlUrlReader;
    @Autowired
    private Parser parser;
    @Autowired
    private ArticlesParsingStrategyFactory articlesParsingStrategyFactory;

    private int chunksNumber = 2;

    public Iterable<Article> findAll() {
        return articlesDao.findAll();
    }

    public void parse(SearchRequest searchRequest) {

        ExecutorService executorService = Executors.newFixedThreadPool(chunksNumber);
        for (ArticlesReaderThread articlesReaderThread : createArticlesWorkers(searchRequest)) {
            LOGGER.info("started new thread");
            executorService.execute(articlesReaderThread);
        }
        executorService.shutdown();

        parseArticlesFromHtml(searchRequest.getSite());
    }

    private List<ArticlesReaderThread> createArticlesWorkers(SearchRequest searchRequest) {
        List<ArticlesReaderThread> result = new ArrayList<>();
        if (searchRequest.getPriceTo() - searchRequest.getPriceFrom() >= 1000) {
            int delta = (searchRequest.getPriceTo() - searchRequest.getPriceFrom()) / chunksNumber;
            int nextPriceTo = searchRequest.getPriceTo() - delta;
            result.add(new ArticlesReaderThread(
                    new SearchRequest(searchRequest.getSite(),
                            searchRequest.getPriceFrom(),
                            nextPriceTo,
                            searchRequest.getYearFrom(),
                            searchRequest.getMark(),
                            searchRequest.getModel())));

            result.add(new ArticlesReaderThread(
                    new SearchRequest(searchRequest.getSite(),
                            nextPriceTo + 1,
                            searchRequest.getPriceTo(),
                            searchRequest.getYearFrom(),
                            searchRequest.getMark(),
                            searchRequest.getModel())));
        } else {
            result.add(new ArticlesReaderThread(searchRequest));
        }

        return result;
    }

    public void parseArticlesFromHtml(AutoSite site) {
        AbstractArticlesParsingStrategy articlesParsingStrategy = articlesParsingStrategyFactory.create(site);

        try {
            File folder = new File(PATH_TO_HTMLS);

            for (File file : folder.listFiles()) {
                if (file.getCanonicalPath().contains(".html")
                        && file.getCanonicalPath().contains(articlesParsingStrategy.getSiteName())) {
                    List<Article> articles = parser.parseArticlesFromHtmlFile(file, articlesParsingStrategy);
                    LOGGER.info("PARSING ARTICLES "+file.getName()+" DONE!");
                    articlesDao.save(articles);
                    LOGGER.info("SAVING ARTICLES "+file.getName()+" DONE!");
                }
            }
        } catch (IOException e) {
            LOGGER.error(e);
        }
    }

    public void parseDetailsPages(AutoSite site) {
        List<String> urls = StreamSupport.stream(articlesDao.findAll().spliterator(), false)
                .map(Article::getDetailsUrl)
                .collect(toList());
        htmlUrlReader.readDetailPages(urls);
    }

    class ArticlesReaderThread implements Runnable {

        public ArticlesReaderThread(SearchRequest searchRequest) {
            this.searchRequest = searchRequest;
        }

        private final SearchRequest searchRequest;

        @Override
        public void run() {
            readHtmlFromSite();
        }

        private void readHtmlFromSite() {
            AbstractUrlBuilder urlBuilder = urlBuildersFactory.create(searchRequest.getSite())
                    .year_from(searchRequest.getYearFrom())
                    .usdPriceFrom(searchRequest.getPriceFrom())
                    .usdPriceTo(searchRequest.getPriceTo());

            AbstractArticlesParsingStrategy articlesParsingStrategy = articlesParsingStrategyFactory.create(searchRequest.getSite());
            htmlUrlReader.read(articlesParsingStrategy, urlBuilder);

            LOGGER.info("READ JOBS DONE FOR SITE: [" + searchRequest.getSite() + "]!");
        }
    }
}
