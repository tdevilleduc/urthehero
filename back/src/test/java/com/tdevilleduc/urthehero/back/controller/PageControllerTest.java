package com.tdevilleduc.urthehero.back.controller;

import com.tdevilleduc.urthehero.back.BackApplication;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = BackApplication.class)
@WebAppConfiguration
public class PageControllerTest {

    private static String uriController = "/Page";

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
    }

    @Test
    public void test_getPageById() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(uriController + "/1"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().string(is(notNullValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.text", Matchers.is("Ulysse")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.image", Matchers.is("image3")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nextPageList", hasSize(3)))
        ;
    }

    @Test
    public void test_getFirstPageByStoryId() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(uriController + "/Story/2"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().string(is(notNullValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(4)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.text", Matchers.is("Voyage au bout de la nuit est le premier roman de Céline, publié en 1932. Ce livre manqua de deux voix le prix Goncourt mais obtient le prix Renaudot1. Il est traduit en 37 langues2.")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.image", Matchers.is("image3")))
        ;
    }

    @Test
    public void test_getAllPagesByStoryId() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(uriController + "/all/Story/1"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().string(is(notNullValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(4)))
        ;
    }

}
