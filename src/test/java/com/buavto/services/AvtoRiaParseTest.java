package com.buavto.services;

import com.buavto.Application;
import com.buavto.model.Article;
import com.buavto.model.Brand;
import org.jsoup.nodes.Document;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class AvtoRiaParseTest {

    private final static String PATH = "C:\\Users\\fein\\IdeaProjects\\BuAvtoAggregator\\src\\test\\resources\\avto_ria_test.html";

    @Autowired
    private Parser parser;
    @Autowired
    private AvtoRiaArticlesParserStrategy articlesParsingStrategy;

    @Test
    public void testParse() throws Exception {
        Document dom = parser.parseDom(readFile(PATH));
        List<Article> articleList = parser.parseArticles(dom, articlesParsingStrategy);

        Assert.assertNotNull(articleList);
    }

    private String readFile(String path) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded);
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
}
