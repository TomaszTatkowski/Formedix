package com.formedix;

import com.formedix.model.CurrencyExchange;
import com.formedix.model.Filter;
import com.formedix.repository.Repository;
import com.formedix.services.Services;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FormedixAppTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private Repository repository;

    @Autowired
    private Services services;

    @Test
    public void testBasicMapping() throws Exception {
        ResponseEntity<String> response = restTemplate.getForEntity(
                new URL("http://localhost:" + port + "/filter-example").toString(), String.class);
        assertEquals("{\"startDateString\":\"2019-12-02\",\"endDateString\":\"2019-12-04\",\"sourceCurrency\":\"USD\",\"targetCurrency\":\"GBP\",\"amount\":0.00}", response.getBody());
    }

    @Test
    public void testMapAndListImmutability(){
        Exception exception = assertThrows(UnsupportedOperationException.class, () -> {
            List<CurrencyExchange> list = new ArrayList<>();
            list.add(new CurrencyExchange("USD", new BigDecimal("2.00")));
            repository.getData().put("test", list);
        });
        assertEquals("java.lang.UnsupportedOperationException", exception.toString());

        exception = assertThrows(UnsupportedOperationException.class, () -> {
            repository.getData().get("2019-07-26").add(new CurrencyExchange("TEST", new BigDecimal("1.00")));
        });
        assertEquals("java.lang.UnsupportedOperationException", exception.toString());
    }

    @Test
    public void testAllExchangeByDate() {
        ResponseEntity<Map<String, List<CurrencyExchange>>> response = services.getConversionsByDate(new Filter("2021-10-12", "2021-10-14", "", "", new BigDecimal("0.00")));
        assertEquals(response.getBody().size(), 3);
        assertEquals(response.getBody().get("2021-10-12").stream().filter(s -> s.getCurrency().equals("USD")).map(s -> s.getExchangeRate().doubleValue()).findAny().get(), 1.1555);
    }

    @Test
    public void testGetConversionAtGivenDate() {
        ResponseEntity<String> response = services.getConversionAtGivenDate(new Filter("2021-10-14", "2021-10-14", "USD", "PLN", new BigDecimal("20.10")));
        assertEquals(response.getBody(), "106.56");
    }

    @Test
    public void testGetHighestConversionAtGivenDates() {
        ResponseEntity<String> response = services.getHighestConversionAtGivenDates(new Filter("2021-09-27", "2021-10-14", "USD", "", new BigDecimal("0.00")));
        assertEquals(response.getBody(), "1.1698");
    }

    @Test
    public void testGetAverageConversionAtGivenDates() {
        ResponseEntity<String> response = services.getAverageConversionAtGivenDates(new Filter("2021-10-10", "2021-10-14", "USD", "", new BigDecimal("0.00")));
        assertEquals(response.getBody(), "1.15733");
    }
}