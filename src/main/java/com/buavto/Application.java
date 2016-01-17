package com.buavto;

import com.buavto.dao.ArticlesDao;
import com.buavto.model.Article;
import com.buavto.services.ArticlesParsingStrategy;
import com.buavto.services.Parser;
import com.buavto.builders.url.AvtoRiaBuilder;
import com.buavto.builders.url.UrlBuildersFactory;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import java.io.IOException;
import java.net.URL;
import java.util.List;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.buavto"})
@ImportResource("classpath:config.xml")
public class Application {
    private final static Logger LOGGER = LogManager.getLogger(Application.class.getName());


    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);

        Parser parser = (Parser) ctx.getBean("parser");
        UrlBuildersFactory urlBuildersFactory = (UrlBuildersFactory) ctx.getBean("urlBuildersFactory");
        ArticlesParsingStrategy parsingStrategy = (ArticlesParsingStrategy) ctx.getBean("avtoRiaArticlesParserStrategy");
        ArticlesDao articlesDao = (ArticlesDao) ctx.getBean("articlesDao");

        AvtoRiaBuilder avtoRiaBuilder = (AvtoRiaBuilder) urlBuildersFactory.create(1);
        URL url = avtoRiaBuilder
                .year_from(2014)
                .currency(1)
                .priceFrom(10000)
                .build();

        try {
            List<Article> articles = parser.parseArticles(url.toString(), parsingStrategy);
            LOGGER.info("articles parsed: " + articles);
            articlesDao.save(articles);
        } catch (IOException e) {
        }

    }
}
