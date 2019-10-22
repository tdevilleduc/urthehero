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
public class StoryControllerTest {

    private static final String uriController = "/Story";

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
    public void test_getStoryById() throws Exception {
        MvcResult resultActions = mockMvc.perform(MockMvcRequestBuilders.get(uriController + "/2"))
                .andExpect(request().asyncStarted())
                .andReturn();
        mockMvc.perform(asyncDispatch(resultActions))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().string(is(notNullValue())))
                .andExpect(jsonPath("$.id", Matchers.is(2)))
                .andExpect(jsonPath("$.title", Matchers.is("Voyage au bout de la nuit")))
                .andExpect(jsonPath("$.authorId", Matchers.is(2)))
                .andExpect(jsonPath("$.firstPageId", Matchers.is(4)))
                .andExpect(jsonPath("$.currentPageId", Matchers.is(Matchers.emptyOrNullString())))
                .andExpect(jsonPath("$.numberOfPages", Matchers.is(3)))
                .andExpect(jsonPath("$.numberOfReaders", Matchers.is(2)))
        ;
    }

    @Test
    public void test_getAllStories() throws Exception {
        MvcResult resultActions = mockMvc.perform(MockMvcRequestBuilders.get(uriController + "/all"))
                .andExpect(request().asyncStarted())
                .andReturn();
        mockMvc.perform(asyncDispatch(resultActions))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().string(is(notNullValue())))
                .andExpect(jsonPath("$", hasSize(3)))
        ;
    }

    @Test
    public void test_getStoryByPersonId() throws Exception {
        MvcResult resultActions = mockMvc.perform(MockMvcRequestBuilders.get(uriController + "/all/Person/1"))
                .andExpect(request().asyncStarted())
                .andReturn();
        mockMvc.perform(asyncDispatch(resultActions))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().string(is(notNullValue())))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", Matchers.is(2)))
                .andExpect(jsonPath("$[0].title", Matchers.is("Voyage au bout de la nuit")))
                .andExpect(jsonPath("$[0].authorId", Matchers.is(2)))
                .andExpect(jsonPath("$[0].firstPageId", Matchers.is(4)))
                .andExpect(jsonPath("$[0].currentPageId", Matchers.is(3)))
                .andExpect(jsonPath("$[0].numberOfPages", Matchers.is(3)))
                .andExpect(jsonPath("$[0].numberOfReaders", Matchers.is(2)))
                .andExpect(jsonPath("$[1].id", Matchers.is(1)))
                .andExpect(jsonPath("$[1].title", Matchers.is("Ulysse")))
                .andExpect(jsonPath("$[1].authorId", Matchers.is(1)))
                .andExpect(jsonPath("$[1].firstPageId", Matchers.is(1)))
                .andExpect(jsonPath("$[1].currentPageId", Matchers.is(2)))
                .andExpect(jsonPath("$[1].numberOfPages", Matchers.is(4)))
                .andExpect(jsonPath("$[1].numberOfReaders", Matchers.is(3)))
        ;
    }

}
