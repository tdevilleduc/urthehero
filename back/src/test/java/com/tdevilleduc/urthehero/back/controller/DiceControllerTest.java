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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = BackApplication.class)
@WebAppConfiguration
public class DiceControllerTest {


    private static final String uriController = "/Dice";

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
        MvcResult resultActions = mockMvc.perform(MockMvcRequestBuilders.get(uriController + "/roll/" + diceString))
                .andExpect(request().asyncStarted())
                .andReturn();
        mockMvc.perform(asyncDispatch(resultActions))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().string(is(notNullValue())))
                .andExpect(jsonPath("$.value", Matchers.isA(Integer.class)))
                .andExpect(jsonPath("$.value", Matchers.greaterThan(0)))
                .andExpect(jsonPath("$.value", Matchers.lessThanOrEqualTo(20)))
                .andExpect(jsonPath("$.dice", Matchers.is(diceString)))
        ;
    }

    @Test
    public void test_rollMulti_thenSuccess() throws Exception {
        String diceString = "DE_20";
        int numberOfRolls = 4;
        MvcResult resultActions = mockMvc.perform(MockMvcRequestBuilders.get(uriController + "/roll/" + diceString + "/" + numberOfRolls))
                .andExpect(request().asyncStarted())
                .andReturn();
        mockMvc.perform(asyncDispatch(resultActions))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().string(is(notNullValue())))
                .andExpect(jsonPath("$", hasSize(numberOfRolls)))
                .andExpect(jsonPath("$[1].value", Matchers.isA(Integer.class)))
                .andExpect(jsonPath("$[1].value", Matchers.greaterThan(0)))
                .andExpect(jsonPath("$[1].value", Matchers.lessThanOrEqualTo(20)))
                .andExpect(jsonPath("$[1].dice", Matchers.is(diceString)))
        ;
    }
}
