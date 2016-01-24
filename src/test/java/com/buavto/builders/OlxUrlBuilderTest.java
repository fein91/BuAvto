package com.buavto.builders;

import com.buavto.Application;
import com.buavto.AutoSite;
import com.buavto.builders.url.OlxUrlBuilder;
import com.buavto.builders.url.UrlBuildersFactory;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class OlxUrlBuilderTest {
    @Autowired
    private UrlBuildersFactory urlBuildersFactory;

    @Test
    public void testBuilder() throws Exception {
        OlxUrlBuilder builder = (OlxUrlBuilder) urlBuildersFactory.create(AutoSite.OLX_UA);

        String expectedUrl1 = "http://olx.ua/uk/transport/legkovye-avtomobili/" +
                "kiev/" +
                "?search[filter_float_price:from]=500000" +
                "&search[filter_float_price:to]=1000000" +
                "&search[filter_float_motor_year:from]=2013" +
                "&search[photos]=1" +
                "&search[private_business]=private" +
                "&currency=USD" +
                "&page=1";

        String url1 = builder
                .region(OlxUrlBuilder.Region.kiev)
                .year_from(2013)
                .usdPriceFrom(500000)
                .usdPriceTo(1000000)
                .build();

        Assert.assertEquals(expectedUrl1, url1);

        builder = (OlxUrlBuilder) urlBuildersFactory.create(AutoSite.OLX_UA);

        String expectedUrl2 = "http://olx.ua/uk/transport/legkovye-avtomobili/" +
                "?search[filter_float_price:from]=0" +
                "&search[filter_float_price:to]=0" +
                "&search[filter_float_motor_year:from]=2015" +
                "&search[photos]=1" +
                "&search[private_business]=private" +
                "&currency=USD" +
                "&page=2";

        String url2 = builder.year_from(2015).page(2).build();

        Assert.assertEquals(expectedUrl2, url2);
    }
}
