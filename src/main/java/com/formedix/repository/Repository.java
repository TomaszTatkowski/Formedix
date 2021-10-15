package com.formedix.repository;

import com.formedix.model.CurrencyExchange;

import java.util.List;
import java.util.Map;

public interface Repository {
    Map<String, List<CurrencyExchange>> getData();

    List<String> getCurrencyList();
}
