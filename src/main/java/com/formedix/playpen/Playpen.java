package com.formedix.playpen;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Playpen {

    public static void main(String[] args) {

//        LoadData loadData = new LoadDataFromCSV();
//
//        loadData.loadData("src/main/resources/eurofxref-hist.csv");

        System.out.println(parseToBigDecimal("N/A").toString());

    }

    private static BigDecimal parseToBigDecimal(String stringToParse) {

        try {
            BigDecimal bd = new BigDecimal(stringToParse);
            bd = bd.setScale(5, RoundingMode.HALF_UP);
            return bd;
        } catch (NumberFormatException e) {
            return BigDecimal.valueOf(-1);
        }
    }
}
