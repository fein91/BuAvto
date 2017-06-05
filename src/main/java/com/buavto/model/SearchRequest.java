package com.buavto.model;

import com.buavto.AutoSite;

public class SearchRequest {

    public SearchRequest(AutoSite site, int priceFrom, int priceTo, int yearFrom, int mark, int model) {
        this.site = site;
        this.priceFrom = priceFrom;
        this.priceTo = priceTo;
        this.yearFrom = yearFrom;
        this.mark = mark;
        this.model = model;
    }

    private final AutoSite site;
    private final int priceFrom;
    private final int priceTo;
    private final int yearFrom;
    private final int mark;
    private final int model;

    public AutoSite getSite() {
        return site;
    }

    public int getPriceFrom() {
        return priceFrom;
    }

    public int getPriceTo() {
        return priceTo;
    }

    public int getYearFrom() {
        return yearFrom;
    }

    public int getMark() {
        return mark;
    }

    public int getModel() {
        return model;
    }
}
