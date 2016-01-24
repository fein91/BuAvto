package com.buavto;

import com.buavto.builders.url.AbstractUrlBuilder;
import com.buavto.dao.ArticlesDao;
import com.buavto.model.Article;
import com.buavto.services.*;
import com.buavto.builders.url.UrlBuildersFactory;
import com.buavto.strategies.AbstractArticlesParsingStrategy;
import com.buavto.strategies.ArticlesParsingStrategyFactory;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.buavto"})
@ImportResource("classpath:config.xml")
public class Application {
    private final static Logger LOGGER = LogManager.getLogger(Application.class.getName());
    private final static String PATH_TO_HTMLS = ".\\target\\htmls\\";

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);
//          readHtmlFromSite(ctx, AutoSite.AVTO_BAZAR);
//        readHtmlFromSite(ctx, AutoSite.AUTO_RIA_COM);
//        readHtmlFromSite(ctx, AutoSite.RST_UA);
//        readHtmlFromSite(ctx, AutoSite.OLX_UA);
        parseArticles(ctx, AutoSite.AUTO_RIA_COM);
        parseArticles(ctx, AutoSite.RST_UA);
        parseArticles(ctx, AutoSite.OLX_UA);
        parseArticles(ctx, AutoSite.AVTO_BAZAR);
    }

    private static void readHtmlFromSite(ApplicationContext ctx, AutoSite autoSite) {
        HtmlUrlReader htmlUrlReader = (HtmlUrlReader) ctx.getBean("htmlUrlReader");
        UrlBuildersFactory urlBuildersFactory = (UrlBuildersFactory) ctx.getBean("urlBuildersFactory");
        ArticlesParsingStrategyFactory articlesParsingStrategyFactory = (ArticlesParsingStrategyFactory) ctx.getBean("articlesParsingStrategyFactory");

        AbstractUrlBuilder rstUrlBuilder = urlBuildersFactory.create(autoSite)
                .year_from(2014)
                .usdPriceFrom(12000)
                .usdPriceTo(14000);

        AbstractArticlesParsingStrategy articlesParsingStrategy = articlesParsingStrategyFactory.create(autoSite);
        htmlUrlReader.read(articlesParsingStrategy, rstUrlBuilder);

        LOGGER.info("READ JOBS DONE FOR SITE: [" + autoSite + "]!");
    }

    private static void parseArticles(ApplicationContext ctx, AutoSite autoSite) {
        Parser parser = (Parser) ctx.getBean("parser");
        ArticlesDao articlesDao = (ArticlesDao) ctx.getBean("articlesDao");
        ArticlesParsingStrategyFactory articlesParsingStrategyFactory = (ArticlesParsingStrategyFactory) ctx.getBean("articlesParsingStrategyFactory");
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



