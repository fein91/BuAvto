package com.buavto.services;

import com.buavto.Application;
import com.buavto.dao.BrandsDao;
import com.buavto.dao.ModelsDao;
import com.buavto.dao.OptionsDao;
import com.buavto.model.Article;
import com.buavto.model.Brand;
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
        Article qashqaiArticle = articleList.get(0);
        Assert.assertEquals("NISSAN", qashqaiArticle.getBrand().getName());
        Assert.assertEquals("QASHQAI", qashqaiArticle.getModel().getName());
        Assert.assertEquals("2015", new SimpleDateFormat("YYYY").format(qashqaiArticle.getYear()));
        Assert.assertEquals(20945L , qashqaiArticle.getPrice());
        Assert.assertEquals("https://auto.ria.com/auto_nissan_qashqai_6996251.html", qashqaiArticle.getDetailsUrl());
        Assert.assertEquals("https://cdn.riastatic.com/photosnew/auto/photo/nissan_qashqai__106296820m.jpg", qashqaiArticle.getPhotoUrl());
        Assert.assertEquals("QASHQAI  XE TURBO", qashqaiArticle.getTitle());

        Assert.assertEquals(10, articleList.size());
    }
}
