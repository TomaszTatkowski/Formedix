package com.formedix.repository;

import com.formedix.config.AppConfig;
import com.formedix.model.CurrencyExchange;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@org.springframework.stereotype.Repository
public class CSVRepository implements Repository {

    @Autowired
    AppConfig appConfig;

    private Map<String, List<CurrencyExchange>> immutableMap;
    private List<String> currencyList;

    @PostConstruct
    private void loadDataOnStartUp() throws IOException {
        loadData(appConfig.getDataSource());
    }


    private void loadData(String dataSource) throws IOException {
        Map<String, List<CurrencyExchange>> tempMap = new HashMap<>();
        String[] token;
        String[] currencyName = new String[1];
        int lineNo = 0;
        String fileContent;

        fileContent = Files.readString(Paths.get(dataSource), StandardCharsets.UTF_8);
        CSVReader fileReader = new CSVReader(new StringReader(fileContent));
        while ((token = fileReader.readNext()) != null) {
            if (lineNo == 0) {
                currencyName = token;
                currencyList = Arrays.asList(Arrays.copyOfRange(currencyName, 1, currencyName.length - 1));
                lineNo++;
                continue;
            }
            String dateString = token[0];
            List<CurrencyExchange> currencyList = new ArrayList<>();
            for (int i = 1 ; token.length -1  > i ; i++ ) {
                CurrencyExchange currencyExchangeEntry = new CurrencyExchange(currencyName[i], parseToBigDecimal(token[i]));
                currencyList.add(currencyExchangeEntry);
            }
            tempMap.put(dateString, ImmutableList.copyOf(currencyList));
            lineNo++;
        }

        LinkedHashMap<String, List<CurrencyExchange>> sortedMap = new LinkedHashMap<>();

        //sort map by key
        tempMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey(Comparator.reverseOrder()))
                .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));

        immutableMap = ImmutableMap.copyOf(sortedMap);
    }

    private BigDecimal parseToBigDecimal(String stringToParse) {

        try {
            BigDecimal bd = new BigDecimal(stringToParse);
            bd = bd.setScale(5, RoundingMode.HALF_UP); //5 - max number of decimal places after analysing data
            return bd;
        } catch (NumberFormatException e) {
            return BigDecimal.valueOf(0);
        }
    }

    @Override
    public Map<String, List<CurrencyExchange>> getData() {
        return immutableMap;
    }

    @Override
    public List<String> getCurrencyList() {
        return currencyList;
    }
}
