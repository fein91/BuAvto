package com.buavto.builders;

import com.buavto.Application;
import com.buavto.AutoSite;
import com.buavto.builders.url.RstUrlBuilder;
import com.buavto.builders.url.UrlBuildersFactory;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class RstUrlBuilderTest {
    @Autowired
    private UrlBuildersFactory urlBuildersFactory;

    @Test
    public void testBuilder() throws Exception {
        RstUrlBuilder builder = (RstUrlBuilder) urlBuildersFactory.create(AutoSite.RST_UA);

        String expectedUrl1 = "http://rst.ua/oldcars/?task=newresults" +
                "&make[]=0" +
                "&year[]=2014" +
                "&year[]=0" +
                "&price[]=11000" +
                "&price[]=14000" +
                "&photos=1" +
                "&engine[]=0" +
                "&engine[]=0" +
                "&gear=0" +
                "&fuel=0" +
                "&drive=0" +
                "&condition=0" +
                "&from=sform" +
                "&start=0" +
                "&body[]=1" +
                "&body[]=2" +
                "&body[]=3" +
                "&body[]=4" +
                "&body[]=5" +
                "&body[]=6" +
                "&body[]=10" +
                "&body[]=11" +
                "&body[]=27";

        String url1 = builder
                .year_from(2014)
                .priceFrom(11000)
                .priceTo(14000)
                .build();

        Assert.assertEquals(expectedUrl1, url1);

        builder = (RstUrlBuilder) urlBuildersFactory.create(AutoSite.RST_UA);

        String expectedUrl2 = "http://rst.ua/oldcars/?task=newresults" +
                "&make[]=0" +
                "&year[]=2015" +
                "&year[]=0" +
                "&price[]=0" +
                "&price[]=0" +
                "&photos=1" +
                "&engine[]=0" +
                "&engine[]=0" +
                "&gear=0" +
                "&fuel=0" +
                "&drive=0" +
                "&condition=0" +
                "&from=sform" +
                "&start=1" +
                "&body[]=1" +
                "&body[]=2" +
                "&body[]=3" +
                "&body[]=4" +
                "&body[]=5" +
                "&body[]=6" +
                "&body[]=10" +
                "&body[]=11" +
                "&body[]=27";
        ;

        String url2 = builder.year_from(2015).page(1).build();

        Assert.assertEquals(expectedUrl2, url2);
    }
}
