package com.tdevilleduc.urthehero.back.controller

import com.tdevilleduc.urthehero.back.AbstractTest
import com.tdevilleduc.urthehero.back.BackApplication
import org.hamcrest.Matchers
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [BackApplication::class])
@WebAppConfiguration
class ProgressionControllerTest : AbstractTest() {

    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var webApplicationContext: WebApplicationContext

    private val uriController: String = "/api/progression"

    @BeforeEach
    fun setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build()
    }

    @Test
    fun test_getAllByUserId_thenSuccess() {
        val userId = 1
        val resultActions = mockMvc.perform(MockMvcRequestBuilders.get("$uriController/user/$userId/all"))
                .andExpect(MockMvcResultMatchers.request().asyncStarted())
                .andReturn()
        mockMvc.perform(MockMvcRequestBuilders.asyncDispatch(resultActions))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().string(Matchers.`is`(Matchers.notNullValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize<Any?>(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].userId", Matchers.`is`(userId)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].storyId", Matchers.`is`(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].actualPageId", Matchers.`is`(3)))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[1].userId", Matchers.`is`(userId)))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[1].storyId", Matchers.`is`(1)))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[1].actualPageId", Matchers.`is`(2)))
    }

    @Test
    fun test_getOneByUserIdAndStoryId_thenSuccess() {
        val userId = 3
        val storyId = 2
        val resultActions = mockMvc.perform(MockMvcRequestBuilders.get("$uriController/user/$userId/story/$storyId"))
                .andExpect(MockMvcResultMatchers.request().asyncStarted())
                .andReturn()
        mockMvc.perform(MockMvcRequestBuilders.asyncDispatch(resultActions))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().string(Matchers.`is`(Matchers.notNullValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId", Matchers.`is`(userId)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.storyId", Matchers.`is`(storyId)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.actualPageId", Matchers.`is`(6)))
    }

    @Test
    fun test_postProgressionAction_thenSuccess() {
        val userId = 1
        val storyId = 1
        val newPageId = 8
        val resultActions = mockMvc.perform(MockMvcRequestBuilders.post("$uriController/user/$userId/story/$storyId/page/$newPageId"))
                .andExpect(MockMvcResultMatchers.request().asyncStarted())
                .andReturn()
        mockMvc.perform(MockMvcRequestBuilders.asyncDispatch(resultActions))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().string(Matchers.`is`(Matchers.notNullValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId", Matchers.`is`(userId)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.storyId", Matchers.`is`(storyId)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.actualPageId", Matchers.`is`(newPageId)))
    }
}
