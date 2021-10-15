package com.formedix.web;

import com.formedix.services.Services;
import com.formedix.model.CurrencyExchange;
import com.formedix.model.Filter;
import com.formedix.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "/")
public class FormedixController {

    @Autowired
    Services services;

    @Autowired
    Repository repository;

    @GetMapping(value="/all-exchange-by-date", consumes= MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, List<CurrencyExchange>> getAllExchangeByDate(@RequestBody Filter filter) {
        return services.getDataByDate(filter);
    }





    @GetMapping(value="filter-example", produces = MediaType.APPLICATION_JSON_VALUE)
    public Filter getFilterExample() {
        return new Filter("2019-12-02", "2019-12-04", "USD", "GBP", new BigDecimal("0.00"));
    }

    @GetMapping(value="available-currency", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> getAvailableCurrency() {
        return repository.getCurrencyList();
    }
}
