package com.formedix.services;

import com.formedix.model.CurrencyExchange;
import com.formedix.model.Filter;
import com.formedix.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ServicesImpl implements Services {

    @Autowired
    Repository repository;

    @Override
    public ResponseEntity<Map<String, List<CurrencyExchange>>> getConversionsByDate(Filter filter) {

        //TODO bad response when date string in bad format
        Map<String, List<CurrencyExchange>> response = repository.getData().entrySet().stream().filter(date -> compareDates(date.getKey(), filter.getStartDateString(), filter.getEndDateString())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> getConversionAtGivenDate(Filter filter) {
        Map<String, List<CurrencyExchange>> resultMap = repository.getData().entrySet().stream().filter(date -> compareDates(date.getKey(), filter.getStartDateString(), filter.getEndDateString())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        Optional<BigDecimal> source = resultMap.get(filter.getStartDateString()).stream().filter(currency -> currency.getCurrency().equals(filter.getSourceCurrency())).map(CurrencyExchange::getExchangeRate).findAny();
        Optional<BigDecimal> target = resultMap.get(filter.getStartDateString()).stream().filter(currency -> currency.getCurrency().equals(filter.getTargetCurrency())).map(CurrencyExchange::getExchangeRate).findAny();

        if (source.isEmpty() || target.isEmpty()) {
            return new ResponseEntity<String>("No data for given currency ", HttpStatus.OK);
        }

        if (source.get().equals(new BigDecimal(-1)) || target.get().equals(new BigDecimal(-1))) {
            return new ResponseEntity<String>("No data at given date", HttpStatus.OK);
        }

        BigDecimal inEuro = source.get().multiply(new BigDecimal(String.valueOf(filter.getAmount())));

        return new ResponseEntity<>(String.valueOf(target.get().multiply(inEuro).setScale(2, RoundingMode.HALF_UP).doubleValue()), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<String> getHighestConversionAtGivenDates(Filter filter) {
        Map<String, List<CurrencyExchange>> resultMap = repository.getData().entrySet().stream().filter(date -> compareDates(date.getKey(), filter.getStartDateString(), filter.getEndDateString())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        List<CurrencyExchange> temp = new ArrayList<>();
        resultMap.forEach((key, value) -> temp.addAll(value));
        List<BigDecimal> listOfSourceCurrency = temp.stream().filter(s -> s.getCurrency().equals(filter.getSourceCurrency())).map(CurrencyExchange::getExchangeRate).sorted(Collections.reverseOrder()).collect(Collectors.toList());

        return new ResponseEntity<>(String.valueOf(listOfSourceCurrency.get(0).doubleValue()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> getAverageConversionAtGivenDates(Filter filter) {
        Map<String, List<CurrencyExchange>> resultMap = repository.getData().entrySet().stream().filter(date -> compareDates(date.getKey(), filter.getStartDateString(), filter.getEndDateString())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        List<CurrencyExchange> temp = new ArrayList<>();
        resultMap.forEach((key, value) -> temp.addAll(value));
        List<BigDecimal> listOfSourceCurrency = temp.stream().filter(s -> s.getCurrency().equals(filter.getSourceCurrency())).map(CurrencyExchange::getExchangeRate)
                .filter(s -> !s.equals(BigDecimal.valueOf(-1))).collect(Collectors.toList());

        if(listOfSourceCurrency.size() == 0) {
            return new ResponseEntity<>("No data available for date range", HttpStatus.OK);
        }

        BigDecimal sum = listOfSourceCurrency.stream().reduce(BigDecimal.ZERO, BigDecimal::add);

        return new ResponseEntity<>(String.valueOf(sum.divide(BigDecimal.valueOf(listOfSourceCurrency.size()), RoundingMode.HALF_UP).doubleValue()), HttpStatus.OK);
    }



    private boolean compareDates(String dateToCompare, String startDate, String endDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateCompare = null;
        Date dateStart = null;
        Date dateEnd = null;
        try {
            dateCompare = sdf.parse(dateToCompare);
            dateStart = sdf.parse(startDate);
            dateEnd = sdf.parse(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        if (dateCompare.equals(dateStart) && dateCompare.equals(dateEnd)) {
            return true;
        }
        return (dateCompare.equals(dateStart) || dateCompare.after(dateStart)) && (dateCompare.equals(dateEnd) || dateCompare.before(dateEnd));

    }
}
