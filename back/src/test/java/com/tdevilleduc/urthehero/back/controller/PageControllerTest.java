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
public class PageControllerTest {

    private static String uriController = "/Page";

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
    public void test_getPageById() throws Exception {
        MvcResult resultActions = mockMvc.perform(MockMvcRequestBuilders.get(uriController + "/1"))
                .andExpect(request().asyncStarted())
                .andReturn();
        mockMvc.perform(asyncDispatch(resultActions))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().string(is(notNullValue())))
                .andExpect(jsonPath("$.id", Matchers.is(1)))
                .andExpect(jsonPath("$.text", Matchers.is("Ulysse")))
                .andExpect(jsonPath("$.image", Matchers.is("image3")))
                .andExpect(jsonPath("$.nextPageList", hasSize(3)))
        ;
    }

    @Test
    public void test_getFirstPageByStoryId() throws Exception {
        MvcResult resultActions = mockMvc.perform(MockMvcRequestBuilders.get(uriController + "/Story/2"))
                .andExpect(request().asyncStarted())
                .andReturn();
        mockMvc.perform(asyncDispatch(resultActions))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().string(is(notNullValue())))
                .andExpect(jsonPath("$.id", Matchers.is(4)))
                .andExpect(jsonPath("$.text", Matchers.is("Voyage au bout de la nuit est le premier roman de Céline, publié en 1932. Ce livre manqua de deux voix le prix Goncourt mais obtient le prix Renaudot1. Il est traduit en 37 langues2.")))
                .andExpect(jsonPath("$.image", Matchers.is("image3")))
        ;
    }

    @Test
    public void test_getAllPagesByStoryId() throws Exception {
        MvcResult resultActions = mockMvc.perform(MockMvcRequestBuilders.get(uriController + "/all/Story/1"))
                .andExpect(request().asyncStarted())
                .andReturn();
        mockMvc.perform(asyncDispatch(resultActions))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().string(is(notNullValue())))
                .andExpect(jsonPath("$", hasSize(4)))
        ;
    }

}
