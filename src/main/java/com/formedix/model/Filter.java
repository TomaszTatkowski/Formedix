package com.formedix.model;

import java.math.BigDecimal;

public class Filter {
    private final String startDateString;
    private final String endDateString;
    private final String sourceCurrency;
    private final String targetCurrency;
    private final BigDecimal amount;

    public Filter(String startDateString, String endDateString, String sourceCurrency, String targetCurrency, BigDecimal amount) {
        this.startDateString = startDateString;
        this.endDateString = endDateString;
        this.sourceCurrency = sourceCurrency;
        this.targetCurrency = targetCurrency;
        this.amount = amount;
    }

    public String getStartDateString() {
        return startDateString;
    }

    public String getEndDateString() {
        return endDateString;
    }

    public String getSourceCurrency() {
        return sourceCurrency;
    }

    public String getTargetCurrency() {
        return targetCurrency;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
