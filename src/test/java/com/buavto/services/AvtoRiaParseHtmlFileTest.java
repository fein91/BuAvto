package com.buavto.services;

import com.buavto.Application;
import com.buavto.model.Article;
import com.buavto.model.Brand;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class AvtoRiaParseHtmlFileTest {

    private final static String HTML1_PATH = ".\\src\\test\\resources\\avto_ria_1453498117047_page1.html";
    private final static String HTML2_PATH = ".\\src\\test\\resources\\avto_ria_1453498117047_page2.html";

    @Autowired
    private Parser parser;
    @Autowired
    private AvtoRiaArticlesParserStrategy articlesParsingStrategy;

    @Test
    public void testParse() throws Exception {
        List<Article> articleList = parser.parseArticlesFromHtmlFile(HTML1_PATH, articlesParsingStrategy);

        Assert.assertNotNull(articleList.get(0).getBrand());
        Assert.assertNotNull(articleList.get(0).getModel());
        Assert.assertNotNull(articleList.get(0).getYear());
        Assert.assertNotNull(articleList.get(0).getPrice());
        Assert.assertNotNull(articleList.get(0).getDetailsUrl());
        Assert.assertNotNull(articleList.get(0).getPhotoUrl());

        Assert.assertEquals(10, articleList.size());
    }

    @Test
    public void testParseYear() throws Exception {
        String title1 = "Nissan 2014 Super";

        String year1 = articlesParsingStrategy.parseYear(title1);
        Assert.assertEquals("2014", year1);

        String title2 = "Nissan 2015Super";

        String year2 = articlesParsingStrategy.parseYear(title2);
        Assert.assertEquals("2015", year2);
    }

    @Test
    public void testParseYear1() throws Exception {
        String title1 = "Nissan 2014Super".toUpperCase();
        Brand brand1 = articlesParsingStrategy.parseBrand(title1);
        Assert.assertEquals("NISSAN", brand1.getName());

        String title2 = "KiaRio".toUpperCase();
        Brand brand2 = articlesParsingStrategy.parseBrand(title2);
        Assert.assertEquals("KIA", brand2.getName());
    }

    @Test
    public void testHasNextPage() throws Exception {
        Assert.assertTrue(articlesParsingStrategy.hasNextPage(parser.parseDomFromFile(HTML1_PATH)));

        Assert.assertFalse(articlesParsingStrategy.hasNextPage(parser.parseDomFromFile(HTML2_PATH)));
    }
}
