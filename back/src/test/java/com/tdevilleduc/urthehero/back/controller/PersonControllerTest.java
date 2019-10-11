package com.tdevilleduc.urthehero.back.controller;

import com.tdevilleduc.urthehero.back.BackApplication;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = BackApplication.class)
@WebAppConfiguration
public class PersonControllerTest {

    private static String uriController = "/Person";

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;


    @BeforeEach
    public void setup() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testGetAllPersons() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(uriController + "/all"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().string(is(notNullValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)))
        ;
    }

    @Test
    public void testGetPersonById() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(uriController + "/2"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().string(is(notNullValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.login", Matchers.is("mgianesini")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.displayName", Matchers.is("Marion Gianesini")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", Matchers.is("marion@gmail.com")))
        ;
    }
}
