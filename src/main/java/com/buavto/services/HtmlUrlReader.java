package com.buavto.services;

import com.buavto.builders.url.AbstractUrlBuilder;
import com.buavto.strategies.AbstractArticlesParsingStrategy;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class HtmlUrlReader {
    private final static Logger LOGGER = LogManager.getLogger(HtmlUrlReader.class.getName());

    @Value("${path_to_phatomjs}")
    public String phatomjsPath;
    @Value("${path_to_load_html_js}")
    public String htmljsPath;
    @Value("${path_to_htmls}")
    public String htmlsPath;

    @Autowired
    private Parser parser;

    public void read(AbstractArticlesParsingStrategy articlesParsingStrategy, AbstractUrlBuilder filledUrlBuilder) {
        String url = filledUrlBuilder.build();
        final long readProcessId = System.currentTimeMillis();
        boolean hasNextPage = true;

        while (hasNextPage) {
            String destPath = htmlsPath + articlesParsingStrategy.getSiteName() + readProcessId +"_page" + filledUrlBuilder.getPage() + ".html";

            readPage(url, destPath, articlesParsingStrategy.getLoadTimeout());
            hasNextPage = articlesParsingStrategy.hasNextPage(parser.parseDomFromFile(destPath));

            url = filledUrlBuilder.nextPage().build();
        }
    }

    public void readDetailPages(List<String> urls) {
        urls.parallelStream().forEach(url -> {
            String destPath = htmlsPath + url + ".html";

            readPage(url, destPath, 1000);
        });
    }

    public void readPage(String url, String destPath, long timeout) {
        String cmd = phatomjsPath + " " + htmljsPath + " " + url + " " + destPath + " " + timeout;
        Process p;
        try {
            p = Runtime.getRuntime().exec(cmd);
            p.waitFor();
            LOGGER.info("URL read: [" + url + "] and written to file: [" + destPath + "]");
        } catch (IOException e) {
            LOGGER.error(e);
        } catch (InterruptedException e) {
            LOGGER.error(e);
        }
    }

}
