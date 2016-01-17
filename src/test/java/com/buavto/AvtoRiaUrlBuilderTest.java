package com.buavto;

import com.buavto.builders.url.AvtoRiaBuilder;
import com.buavto.builders.url.UrlBuildersFactory;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.net.URL;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class AvtoRiaUrlBuilderTest {

    @Autowired
    private UrlBuildersFactory urlBuildersFactory;

    @Test
    public void testBuilder() throws Exception {
        AvtoRiaBuilder builder = (AvtoRiaBuilder) urlBuildersFactory.create(1);

        String expectedUrl1 = "https://auto.ria.com/search/" +
                "?category_id=1&marka_id=0&model_id=0&state=0" +
                "#category_id=1" +
                "&marka_id[0]=0" +
                "&model_id[0]=0" +
                "&state[0]=0" +
                "&s_yers[0]=2014" +
                "&po_yers[0]=0" +
                "&price_ot=0" +
                "&price_do=0" +
                "&currency=0" +
                "&countpage=0" +
                "&page=0";

        URL url1 = builder.year_from(2014).build();

        Assert.assertEquals(expectedUrl1, url1.toString());

        builder = (AvtoRiaBuilder) urlBuildersFactory.create(1);

        String expectedUrl2 = "https://auto.ria.com/search/" +
                "?category_id=1&marka_id=0&model_id=0&state=0" +
                "#category_id=1" +
                "&marka_id[0]=0" +
                "&model_id[0]=0" +
                "&state[0]=0" +
                "&s_yers[0]=0" +
                "&po_yers[0]=0" +
                "&price_ot=0" +
                "&price_do=0" +
                "&currency=1" +
                "&countpage=0" +
                "&page=0";

        URL url2 = builder.currency(1).build();

        Assert.assertEquals(expectedUrl2, url2.toString());
    }
}
