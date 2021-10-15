package com.formedix;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.formedix.model.CurrencyExchange;
import com.formedix.repository.Repository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

@WebMvcTest
class FormedixControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Repository repository;

    @Test
    void shouldReturnHelloFromModulr() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/filter-example"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("startDateString")));
    }

    @Test
    void checkMapImmutability() throws Exception {

        List<CurrencyExchange> list = new ArrayList<>();
        repository.getData().put("TEST", list);
    }
}