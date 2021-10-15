package com.formedix.services;

import com.formedix.model.CurrencyExchange;
import com.formedix.model.Filter;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface Services {
    ResponseEntity<Map<String, List<CurrencyExchange>>> getDataByDate(Filter filter);

    /*
    - Given a Date, source Currency (eg. JPY), target Currency (eg. GBP), and an
Amount, returns the Amount given converted from the first to the second Currency as
it would have been on that Date (assuming zero fees).
     */

   /*
- Given a start Date, an end Date and a Currency, return the highest reference
exchange rate that the Currency achieved for the period.
     */

    /*
- Given a start Date, an end Date and a Currency, determine and return the average
reference exchange rate of that Currency for the period.
     */
}
