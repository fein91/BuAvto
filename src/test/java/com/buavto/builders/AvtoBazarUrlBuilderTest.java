package com.buavto.builders;

import com.buavto.Application;
import com.buavto.AutoSite;
import com.buavto.builders.url.AvtoBazarUrlBuilder;
import com.buavto.builders.url.AvtoRiaUrlBuilder;
import com.buavto.builders.url.UrlBuildersFactory;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class AvtoBazarUrlBuilderTest {

    @Autowired
    private UrlBuildersFactory urlBuildersFactory;

    @Test
    public void testBuilder() throws Exception {
        AvtoBazarUrlBuilder builder = (AvtoBazarUrlBuilder) urlBuildersFactory.create(AutoSite.AVTO_BAZAR);

        String expectedUrl1 = "http://avtobazar.infocar.ua/search-car/" +
                "?new[]=0" +
                "&y1=2014" +
                "&p1=11000" +
                "&p2=13000" +
                "&c[]=0" +
                "&fo=1" +
                "&w=1" +
                "&onp=30";

        String url1 = builder.year_from(2014).usdPriceFrom(11000).usdPriceTo(13000).build();

        Assert.assertEquals(expectedUrl1, url1);

        builder = (AvtoBazarUrlBuilder) urlBuildersFactory.create(AutoSite.AVTO_BAZAR);

        String expectedUrl2 = "http://avtobazar.infocar.ua/search-car/" +
                "?new[]=0" +
                "&y1=0" +
                "&p1=0" +
                "&p2=0" +
                "&c[]=11101" +
                "&fo=1" +
                "&w=2" +
                "&onp=30";

        String url2 = builder.region(AvtoBazarUrlBuilder.Region.KIEV).page(2).build();

        Assert.assertEquals(expectedUrl2, url2);
    }
}
