package com.tdevilleduc.urthehero.back.controller;

import com.tdevilleduc.urthehero.back.BackApplication;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = BackApplication.class)
@WebAppConfiguration
public class DiceControllerTest {


    private static String uriController = "/Dice";

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
    }

    @Test
    public void test_rollOne_thenSuccess() throws Exception {
        String diceString = "DE_20";
        this.mockMvc.perform(MockMvcRequestBuilders.get(uriController + "/roll/" + diceString))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().string(is(notNullValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.value", Matchers.isA(Integer.class)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.value", Matchers.greaterThan(Integer.valueOf(0))))
                .andExpect(MockMvcResultMatchers.jsonPath("$.value", Matchers.lessThanOrEqualTo(Integer.valueOf(20))))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dice", Matchers.is(diceString)))
        ;
    }

    @Test
    public void test_rollMulti_thenSuccess() throws Exception {
        String diceString = "DE_20";
        Integer numberOfRolls = 4;
        this.mockMvc.perform(MockMvcRequestBuilders.get(uriController + "/roll/" + diceString + "/" + numberOfRolls))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().string(is(notNullValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(numberOfRolls)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].value", Matchers.isA(Integer.class)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].value", Matchers.greaterThan(Integer.valueOf(0))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].value", Matchers.lessThanOrEqualTo(Integer.valueOf(20))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].dice", Matchers.is(diceString)))
        ;
    }
}
