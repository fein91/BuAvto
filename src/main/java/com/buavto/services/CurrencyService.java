package com.buavto.services;

import com.buavto.Currency;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class CurrencyService {
    private final static String CURRENCY_POINT = "http://resources.finance.ua/ru/public/currency-cash.xml";
    private final static Logger LOGGER = LogManager.getLogger(Parser.class.getName());

    @PostConstruct
    public void init() {
        try {
            Document doc = Jsoup.connect(CURRENCY_POINT).get();
            Element oshadBankContainer = doc.select("#7oiylpmiow8iy1sma9a").first();//Oshadbank id
            Element currenciesContainer = oshadBankContainer.getElementsByTag("currencies").first();
            for (Element currencyContainer : currenciesContainer.getElementsByTag("c")) {
                Currency currency = Currency.valueOf(currencyContainer.id());
                currency.setBuyRate(new BigDecimal(currencyContainer.attributes().get("ar")));
                currency.setSellRate(new BigDecimal(currencyContainer.attributes().get("br")));
                LOGGER.info(currency.string() + " was inited");
            }
        } catch (IOException e) {
            LOGGER.error(e);
        }
    }

    public long convertFromUahToUsd(long uah) {
        return BigDecimal.valueOf(uah)
                .divide(Currency.USD.getBuyRate(), RoundingMode.HALF_UP)
                .longValue();
    }

    public long convertFromUsdToUah(long usd) {
        return BigDecimal.valueOf(usd)
                .multiply(Currency.USD.getSellRate())
                .longValue();
    }
}
