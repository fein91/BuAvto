package com.buavto.services;

import com.buavto.Application;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class CurrencyServiceTest {

    @Autowired
    private CurrencyService currencyService;

    @Test
    public void test() throws Exception {
        long uahToConvert = 300000L;
        long convertedUsd = currencyService.convertFromUahToUsd(uahToConvert);

        Assert.assertNotNull(convertedUsd);
        Assert.assertEquals("Expected rounded to 3 hundred thousands value",
                Math.round(uahToConvert/100000), Math.round(currencyService.convertFromUsdToUah(convertedUsd) / 100000));
    }
}
