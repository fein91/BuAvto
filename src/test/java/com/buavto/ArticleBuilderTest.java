package com.buavto;

import com.buavto.builders.ArticleBuilder;
import com.buavto.model.Article;
import org.junit.Assert;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ArticleBuilderTest {

    @Test
    public void testDateParse() throws Exception {
        Article article =  new ArticleBuilder()
                .year("2014")
                .build();
        Assert.assertEquals("2014", new SimpleDateFormat("YYYY").format(article.getYear()));
    }
}
