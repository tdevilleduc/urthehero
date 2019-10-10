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
public class StoryControllerTest {

    private static String uriController = "/Story";

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
    public void test_getStoryById() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(uriController + "/2"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().string(is(notNullValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("Voyage au bout de la nuit")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.authorId", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstPageId", Matchers.is(4)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.currentPageId", Matchers.is(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfPages", Matchers.is(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfReaders", Matchers.is(2)))
        ;
    }

    @Test
    public void test_getAllStories() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(uriController + "/all"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().string(is(notNullValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)))
        ;
    }

    @Test
    public void test_getStoryByStoryIdAndPersonId() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(uriController + "/2/Person/1"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().string(is(notNullValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("Voyage au bout de la nuit")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.authorId", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstPageId", Matchers.is(4)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.currentPageId", Matchers.is(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfPages", Matchers.is(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfReaders", Matchers.is(2)))
        ;
    }

}
