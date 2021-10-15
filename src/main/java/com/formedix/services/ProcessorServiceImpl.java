package com.formedix.services;

import com.formedix.model.CurrencyExchange;
import com.formedix.model.Filter;
import com.formedix.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProcessorServiceImpl implements Services {

    @Autowired
    Repository repository;

    @Override
    public Map<String, List<CurrencyExchange>> getDataByDate(Filter filter) {
        //TODO implement filtering by date object rather than date string
        return repository.getData().entrySet().stream().filter(date -> date.getKey().equals(filter.getStartDateString())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }


}
