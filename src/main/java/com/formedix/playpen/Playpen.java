package com.formedix.playpen;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Playpen {

    public static void main(String[] args) {

        BigDecimal bd = new BigDecimal("9.00");
        System.out.println(bd.divide(BigDecimal.valueOf(3), RoundingMode.HALF_UP));

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
