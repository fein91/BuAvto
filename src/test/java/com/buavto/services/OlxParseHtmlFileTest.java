package com.buavto.services;

import com.buavto.Application;
import com.buavto.model.Article;
import com.buavto.strategies.OlxArticlesParsingStrategy;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class OlxParseHtmlFileTest {
    private final static String HTML1_PATH = ".\\src\\test\\resources\\olx_ua1453651482534_page1.html";
    private final static String HTML2_PATH = ".\\src\\test\\resources\\olx_ua1453651482534_page33.html";

    @Autowired
    private Parser parser;
    @Autowired
    private OlxArticlesParsingStrategy articlesParsingStrategy;

    @Test
    public void testParse() throws Exception {
        List<Article> articleList = parser.parseArticlesFromHtmlFile(HTML1_PATH, articlesParsingStrategy);
        Article poloArticle = articleList.get(2);
        Assert.assertEquals("VOLKSWAGEN", poloArticle.getBrand().getName());
        Assert.assertEquals("POLO", poloArticle.getModel().getName());
        Assert.assertEquals(12061L, poloArticle.getPrice());
        Assert.assertEquals("http://olx.ua/uk/obyavlenie/novyy-volkswagen-polo-sedan-v-kredit-rassrochku-bez-spravki-o-dohodah-IDgVFYY.html#fb5de94f8d", poloArticle.getDetailsUrl());
        Assert.assertEquals("http://img10.olx.ua/images_slandocomua/265903294_1_261x203_novyy-volkswagen-polo-sedan-v-kreditrassrochku-bez-spravki-o-dohodah-nikolaev.jpg", poloArticle.getPhotoUrl());
        Assert.assertEquals("НОВЫЙ  POLO SEDAN В КРЕДИТ,РАССРОЧКУ БЕЗ СПРАВКИ О ДОХОДАХ", poloArticle.getTitle());

        Assert.assertEquals(39, articleList.size());
    }
}
