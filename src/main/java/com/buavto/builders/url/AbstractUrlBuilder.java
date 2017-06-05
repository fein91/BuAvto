package com.buavto.builders.url;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public abstract class AbstractUrlBuilder implements UrlBuilder {
    protected final static Logger LOGGER = LogManager.getLogger(AbstractUrlBuilder.class.getName());

    protected long model;
    protected long mark;
    protected long s_yers;
    protected long po_yers;
    protected long price_ot;
    protected long price_do;
    protected long page;


    @Override
    public AbstractUrlBuilder mark(long mark) {
        this.mark = mark;
        return this;
    }

    @Override
    public AbstractUrlBuilder model(long model) {
        this.model = model;
        return this;
    }

    @Override
    public AbstractUrlBuilder year_from(long from) {
        this.s_yers = from;
        return this;
    }

    @Override
    public AbstractUrlBuilder year_to(long to) {
        this.po_yers = to;
        return this;
    }

    @Override
    public AbstractUrlBuilder usdPriceFrom(long priceFrom) {
        this.price_ot = priceFrom;
        return this;
    }

    @Override
    public AbstractUrlBuilder usdPriceTo(long priceTo) {
        this.price_do = priceTo;
        return this;
    }

    @Override
    public AbstractUrlBuilder page(long page) {
        this.page = page;
        return this;
    }

    @Override
    public AbstractUrlBuilder nextPage() {
        this.page++;
        return this;
    }

    @Override
    public long getPage() {
        return page;
    }
}
