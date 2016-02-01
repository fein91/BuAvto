package com.buavto.services;

import com.buavto.AutoSite;
import com.buavto.builders.url.AbstractUrlBuilder;
import com.buavto.builders.url.UrlBuildersFactory;
import com.buavto.dao.ArticlesDao;
import com.buavto.model.Article;
import com.buavto.strategies.AbstractArticlesParsingStrategy;
import com.buavto.strategies.ArticlesParsingStrategyFactory;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;

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

    public Iterable<Article> findAll() {
        return articlesDao.findAll();
    }

    public void parse(AutoSite autoSite, int priceFrom, int priceTo, int yearFrom) {
        readHtmlFromSite(autoSite, priceFrom, priceTo, yearFrom);
        parseArticlesFromHtml(autoSite);
    }

    private void readHtmlFromSite(AutoSite autoSite, int priceFrom, int priceTo, int yearFrom) {
        AbstractUrlBuilder rstUrlBuilder = urlBuildersFactory.create(autoSite)
                .year_from(yearFrom)
                .usdPriceFrom(priceFrom)
                .usdPriceTo(priceTo);

        AbstractArticlesParsingStrategy articlesParsingStrategy = articlesParsingStrategyFactory.create(autoSite);
        htmlUrlReader.read(articlesParsingStrategy, rstUrlBuilder);

        LOGGER.info("READ JOBS DONE FOR SITE: [" + autoSite + "]!");
    }

    private void parseArticlesFromHtml(AutoSite autoSite) {
        AbstractArticlesParsingStrategy articlesParsingStrategy = articlesParsingStrategyFactory.create(autoSite);

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
}
