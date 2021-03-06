package com.tdevilleduc.urthehero.back.controller

import com.tdevilleduc.urthehero.back.AbstractITTest
import com.tdevilleduc.urthehero.back.BackApplication
import com.tdevilleduc.urthehero.back.dao.StoryDao
import com.tdevilleduc.urthehero.back.model.Story
import com.tdevilleduc.urthehero.back.util.TestUtil
import org.hamcrest.Matchers
import org.junit.jupiter.api.Assertions
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
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper
import java.util.*

@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [BackApplication::class])
@WebAppConfiguration
internal class StoryControllerTest : AbstractITTest() {

    private lateinit var mockMvc: MockMvc
    @Autowired
    private lateinit var webApplicationContext: WebApplicationContext

    private val objectMapper: ObjectMapper = ObjectMapper()

    private val uriController: String = "/api/story"
    @Autowired
    private lateinit var storyDao: StoryDao

    @BeforeEach
    fun setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build()
    }

    @Test
    fun test_getAllStories() {
        val resultActions = mockMvc.perform(MockMvcRequestBuilders.get("$uriController/all"))
                .andExpect(MockMvcResultMatchers.request().asyncStarted())
                .andReturn()
        mockMvc.perform(MockMvcRequestBuilders.asyncDispatch(resultActions))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().string(Matchers.`is`(Matchers.notNullValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize<Any?>(3)))
    }

    @Test
    fun test_getStoryById() {
        val resultActions = mockMvc.perform(MockMvcRequestBuilders.get("$uriController/2"))
                .andExpect(MockMvcResultMatchers.request().asyncStarted())
                .andReturn()
        mockMvc.perform(MockMvcRequestBuilders.asyncDispatch(resultActions))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().string(Matchers.`is`(Matchers.notNullValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.storyId", Matchers.`is`(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.`is`("Voyage au bout de la nuit")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.authorId", Matchers.`is`(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstPageId", Matchers.`is`(4)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfReaders", Matchers.`is`(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfPages", Matchers.`is`(3)))
    }

    @Test
    fun test_createStory_thenSuccess() {
        val authorId = 1
        val firstPageId = 1
        val storyDto = TestUtil.createStoryDto(authorId, firstPageId)
        storyDto.storyId = 1
        val resultActions = mockMvc.perform(MockMvcRequestBuilders.put(uriController)
                .content(objectMapper.writeValueAsString(storyDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.request().asyncStarted())
                .andReturn()
        mockMvc.perform(MockMvcRequestBuilders.asyncDispatch(resultActions))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError)
    }

    @Test
    fun test_createStory_thenAlreadyExists() {
        val authorId = 1
        val firstPageId = 1
        val storyDto = TestUtil.createStoryDto(authorId, firstPageId)
        val resultActions = mockMvc.perform(MockMvcRequestBuilders.put(uriController)
                .content(objectMapper.writeValueAsString(storyDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.request().asyncStarted())
                .andReturn()
        mockMvc.perform(MockMvcRequestBuilders.asyncDispatch(resultActions))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().string(Matchers.`is`(Matchers.notNullValue())))
    }

    @Test
    fun test_deleteStory_thenSuccess() {
        var story = TestUtil.createStory()
        story = storyDao.save(story)
        Assertions.assertNotNull(story.storyId)
        val optionalBefore: Optional<Story> = storyDao.findById(story.storyId)
        Assertions.assertTrue(optionalBefore.isPresent)

        val resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("$uriController/" + story.storyId))
                .andExpect(MockMvcResultMatchers.request().asyncStarted())
                .andReturn()
        mockMvc.perform(MockMvcRequestBuilders.asyncDispatch(resultActions))
                .andExpect(MockMvcResultMatchers.status().isOk)

        val optionalAfter: Optional<Story> = storyDao.findById(story.storyId)
        Assertions.assertTrue(optionalAfter.isEmpty)
    }

}