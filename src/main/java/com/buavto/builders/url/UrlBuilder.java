package com.buavto.builders.url;

public interface UrlBuilder {
    UrlBuilder usdPriceFrom(long priceFrom);
    UrlBuilder usdPriceTo(long priceTo);
    UrlBuilder year_from(long from);
    UrlBuilder year_to(long to);
    UrlBuilder page(long page);
    UrlBuilder nextPage();
    long getPage();

    String build();

}
