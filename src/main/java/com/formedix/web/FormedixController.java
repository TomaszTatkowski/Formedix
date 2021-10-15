package com.formedix.web;

import com.formedix.services.Services;
import com.formedix.model.CurrencyExchange;
import com.formedix.model.Filter;
import com.formedix.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    /*
    example request:
    {"startDateString":"2021-10-12","endDateString":"2021-10-12","sourceCurrency":"","targetCurrency":"","amount":0.00}
    {"startDateString":"2021-10-12","endDateString":"2021-10-14","sourceCurrency":"","targetCurrency":"","amount":0.00}
     */
    @GetMapping(value="all-exchange-by-date", consumes= MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, List<CurrencyExchange>>> getAllExchangeByDate(@RequestBody Filter filter) {
        return services.getDataByDate(filter);
    }

    /*
    example request:
    {"startDateString":"2021-10-12","endDateString":"2021-10-12","sourceCurrency":"USD","targetCurrency":"GBP","amount":0.00}
     */
    @GetMapping(value="conversion-by-date", consumes= MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getConversionAtGivenDate(@RequestBody Filter filter) {
        return services.getConversionAtGivenDate(filter);
    }

    /*
    example request:
    {"startDateString":"2021-10-11","endDateString":"2021-10-14","sourceCurrency":"USD","targetCurrency":"","amount":0.00}
     */
    @GetMapping(value="highest-conversion-by-date", consumes= MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getHighestConversionAtGivenDates(@RequestBody Filter filter) {
        return services.getHighestConversionAtGivenDates(filter);
    }

    /*
    example request:
    {"startDateString":"2021-10-11","endDateString":"2021-10-14","sourceCurrency":"USD","targetCurrency":"","amount":0.00}
     */
    @GetMapping(value="avg-conversion-by-date", consumes= MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAverageConversionAtGivenDates(@RequestBody Filter filter) {
        return services.getAverageConversionAtGivenDates(filter);
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
