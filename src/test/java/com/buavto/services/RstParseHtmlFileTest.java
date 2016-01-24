package com.buavto.services;

import com.buavto.Application;
import com.buavto.model.Article;
import com.buavto.strategies.RstArticlesParserStrategy;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class RstParseHtmlFileTest {
    private final static String HTML1_PATH = ".\\src\\test\\resources\\rst_1453563857863_page1.html";
    private final static String HTML2_PATH = ".\\src\\test\\resources\\rst_1453563857863_page21.html";

    @Autowired
    private Parser parser;
    @Autowired
    private RstArticlesParserStrategy articlesParsingStrategy;

    @Test
    public void testParse() throws Exception {
        List<Article> articleList = parser.parseArticlesFromHtmlFile(HTML1_PATH, articlesParsingStrategy);

        Assert.assertNotNull(articleList.get(0).getBrand());
        Assert.assertNotNull(articleList.get(0).getModel());
        Assert.assertNotNull(articleList.get(0).getPrice());
        Assert.assertNotNull(articleList.get(0).getDetailsUrl());
        Assert.assertNotNull(articleList.get(0).getPhotoUrl());

        Assert.assertEquals(10, articleList.size());
    }
}
