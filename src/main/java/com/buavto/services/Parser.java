package com.buavto.services;

import com.buavto.model.Article;
import com.buavto.strategies.ArticlesParsingStrategy;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
public class Parser {
    private static final String DEFAULT_USER_AGENT = "Mozilla";
    private final static Logger LOGGER = LogManager.getLogger(Parser.class.getName());


    public List<Article> parseArticlesFromUrl(String url, ArticlesParsingStrategy parsingStrategy) throws IOException {
        Document doc = parseDomFromUrl(url);
        return parseArticles(doc, parsingStrategy);
    }

    public List<Article> parseArticlesFromHtmlFile(File htmlFile, ArticlesParsingStrategy parsingStrategy) throws IOException {
        Document doc = parseDomFromFile(htmlFile);
        return parseArticles(doc, parsingStrategy);
    }

    public List<Article> parseArticlesFromHtmlFile(String htmlPath, ArticlesParsingStrategy parsingStrategy) throws IOException {
        Document doc = parseDomFromFile(htmlPath);
        return parseArticles(doc, parsingStrategy);
    }

    public List<Article> parseArticles(Document doc, ArticlesParsingStrategy parsingStrategy) throws IOException {
        return parsingStrategy.parseDom(doc);
    }

    protected Document parseDomFromUrl(String url) throws IOException {
        Document doc = Jsoup.connect(url)
                .userAgent(DEFAULT_USER_AGENT)
                .get();
        LOGGER.info("html doc was got from url: [" + url + "]");
        return doc;
    }

    protected Document parseDomFromHtml(String html) {
        Document doc = Jsoup.parse(html);
        LOGGER.info("html doc was parsed from html string");
        return doc;
    }

    protected Document parseDomFromFile(File htmlFile) {
        Document doc = null;
        try {
            doc = Jsoup.parse(htmlFile, null);
        } catch (IOException e) {
            LOGGER.error("Parsing html from file: [" + htmlFile.getName() + "] failed", e);
        }
        LOGGER.info("html doc was parsed from file: [" + htmlFile.getName() + "]");
        return doc;
    }

    public Document parseDomFromFile(String htmlPath) {
        Document doc = null;
        try {
            doc = Jsoup.parse(new File(htmlPath), null);
        } catch (IOException e) {
            LOGGER.error("Parsing html from file: [" + htmlPath + "] failed", e);
        }
        LOGGER.info("html doc was parsed from file: [" + htmlPath + "]");
        return doc;
    }
}
