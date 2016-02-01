package com.buavto.strategies;

import com.buavto.Application;
import com.buavto.model.Article;
import com.buavto.model.Brand;
import com.buavto.services.Parser;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.SimpleDateFormat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class AbstractArticlesParsingStrategyTest {
    @Autowired
    private AvtoRiaArticlesParserStrategy articlesParsingStrategy;
    @Autowired
    private Parser parser;

    @Test
    public void testParseTitle() throws Exception {
        String title = "KIA Rio Top 2014";
        Article article = articlesParsingStrategy.createArticleBuilderFromTitle(title).build();

        Assert.assertEquals("KIA", article.getBrand().getName());
        Assert.assertEquals("RIO", article.getModel().getName());
        Assert.assertEquals("TOP", article.getOption().getName());
        Assert.assertEquals("2014", new SimpleDateFormat("YYYY").format(article.getYear()));
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
