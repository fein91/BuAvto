package com.buavto.services;

import com.buavto.model.Article;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class Parser {
    private static final String DEFAULT_USER_AGENT = "Mozilla";
    private final static Logger LOGGER = LogManager.getLogger(Parser.class.getName());

    public Document parseDom(String html) {
        return Jsoup.parse(html);
    }

    public List<Article> parseArticles(String url, ArticlesParsingStrategy parsingStrategy) throws IOException {
        Document doc = Jsoup.connect(url)
                .userAgent(DEFAULT_USER_AGENT)
                .get();
        LOGGER.info("html doc was got [" + url + "]");
        return parseArticles(doc, parsingStrategy);
    }

    public List<Article> parseArticles(Document doc, ArticlesParsingStrategy parsingStrategy) throws IOException {
        return parsingStrategy.parseDom(doc);
    }
}
