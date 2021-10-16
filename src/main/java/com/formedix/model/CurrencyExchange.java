package com.formedix.model;

import java.math.BigDecimal;

public class CurrencyExchange {

    private final String currency;
    private final BigDecimal exchangeRate;

    public CurrencyExchange(String currency, BigDecimal exchangeRate) {
        this.currency = currency;
        this.exchangeRate = exchangeRate;
    }


    public String getCurrency() {
        return currency;
    }

    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }
}
