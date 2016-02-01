package com.buavto;

import java.math.BigDecimal;

public enum Currency {
    USD,
    EUR,
    RUB;

    private BigDecimal buyRate;
    private BigDecimal sellRate;

    public BigDecimal getBuyRate() {
        return buyRate;
    }

    public void setBuyRate(BigDecimal buyRate) {
        this.buyRate = buyRate;
    }

    public BigDecimal getSellRate() {
        return sellRate;
    }

    public void setSellRate(BigDecimal sellRate) {
        this.sellRate = sellRate;
    }

    public String string() {
        return "Currency " +name() + " {" +
                "buyRate=" + buyRate +
                ", sellRate=" + sellRate +
                '}';
    }
}
