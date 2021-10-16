package com.formedix;

import com.formedix.model.CurrencyExchange;
import com.formedix.model.Filter;
import com.formedix.util.LoadDataFromCSV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProcessorService implements Services {

    @Autowired
    LoadDataFromCSV loadData;

    @Override
    public Map<String, List<CurrencyExchange>> getDataByDate(Filter filter) {
        //TODO implement filtering by date object rather than date string
        return loadData.getData().entrySet().stream().filter(date -> date.getKey().equals(filter.getStartDateString())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }


}
