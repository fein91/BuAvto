package com.buavto.strategies;

import com.buavto.Application;
import com.buavto.services.Parser;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class AvtoBazarArticlesParsingStrategyTest {
    private final static String HTML1_PATH = ".\\src\\test\\resources\\avto_bazar1453660831804_page1.html";

    @Autowired
    private AvtoRiaArticlesParserStrategy articlesParsingStrategy;
    @Autowired
    private Parser parser;

    @Test
    public void testHasNextPage() throws Exception {
        Assert.assertFalse(articlesParsingStrategy.hasNextPage(parser.parseDomFromFile(HTML1_PATH)));
    }
}
