package com.buavto.builders.url;

import com.buavto.Currency;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class OlxUrlBuilder extends AbstractUrlBuilder {

    private final static String URL_PREFIX = "http://olx.ua/uk/transport/legkovye-avtomobili/";

    private Region region;
    private Currency currency = Currency.USD;

    public OlxUrlBuilder() {
        page = 1;
    }

    public AbstractUrlBuilder region(Region region) {
        this.region = region;
        return this;
    }

    public AbstractUrlBuilder currency(Currency currency) {
        this.currency = currency;
        return this;
    }

    private final static String TEMPLATE = "http://olx.ua/uk/transport/legkovye-avtomobili/" +
            "kiev/" +
            "?search[filter_float_price:from]=11000" +
            "&search[filter_float_price:to]=14000" +
            "&search[filter_float_motor_year:from]=2013" +
            "&search[photos]=1" +
            "&search[private_business]=private" +
            "&currency=USD" +
            "&page=1";

    @Override
    public String build() {
        StringBuilder strBuilder = new StringBuilder(URL_PREFIX);
        if (region != null) {
            strBuilder.append(region).append("/");
        }
        strBuilder.append("?search[filter_float_price:from]=").append(price_ot)
                .append("&search[filter_float_price:to]=").append(price_do)
                .append("&search[filter_float_motor_year:from]=").append(s_yers)
                .append("&search[photos]=1")
                .append("&search[private_business]=private")
                .append("&currency=").append(currency)
                .append("&page=").append(page);

        String urlString = strBuilder.toString();
        LOGGER.info("[olx.ua] url was build: " + urlString);
        return urlString;
    }

    public static enum Region {
        kiev
    }
}
