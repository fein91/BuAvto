package com.buavto.builders.url;

public interface UrlBuilder {
    UrlBuilder priceFrom(long priceFrom);
    UrlBuilder priceTo(long priceTo);
    UrlBuilder year_from(long from);
    UrlBuilder year_to(long to);
    UrlBuilder page(long page);
    UrlBuilder nextPage();
    long getPage();

    String build();

}
