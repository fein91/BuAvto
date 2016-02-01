package com.buavto.services;

import com.buavto.Application;
import com.buavto.model.Article;
import com.buavto.strategies.AvtoBazarArticlesParsingStrategy;
import com.buavto.strategies.AvtoRiaArticlesParserStrategy;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.SimpleDateFormat;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class AvtoBazarParseHtmlFileTest {
    private final static String HTML1_PATH = ".\\src\\test\\resources\\avto_bazar1453660831804_page1.html";

    @Autowired
    private Parser parser;
    @Autowired
    private AvtoBazarArticlesParsingStrategy articlesParsingStrategy;

    @Test
    public void testParse() throws Exception {
        List<Article> articleList = parser.parseArticlesFromHtmlFile(HTML1_PATH, articlesParsingStrategy);
        Article i20Article = articleList.get(0);
        Assert.assertEquals("HYUNDAI", i20Article.getBrand().getName());
        Assert.assertEquals("I20", i20Article.getModel().getName());
        Assert.assertEquals("2014", new SimpleDateFormat("YYYY").format(i20Article.getYear()));
        Assert.assertEquals(12500L , i20Article.getPrice());
        Assert.assertEquals("http://avtobazar.infocar.ua/car/kievskaya-oblast/kiev/hyundai/i20/hatchback-2014-618693.html", i20Article.getDetailsUrl());
        Assert.assertEquals("http://i3.infocar.ua/img/bazar2/619/618693/4710054_1.jpg", i20Article.getPhotoUrl());
        Assert.assertEquals("I20", i20Article.getTitle());

        Assert.assertEquals(17, articleList.size());
    }
}
