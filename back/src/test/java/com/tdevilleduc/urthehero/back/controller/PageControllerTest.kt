package com.tdevilleduc.urthehero.back.controller

import com.tdevilleduc.urthehero.back.AbstractITTest
import com.tdevilleduc.urthehero.back.BackApplication
import com.tdevilleduc.urthehero.back.dao.PageDao
import com.tdevilleduc.urthehero.back.model.Page
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
internal class PageControllerTest : AbstractITTest() {

    private lateinit var mockMvc: MockMvc
    @Autowired
    private lateinit var webApplicationContext: WebApplicationContext

    private val objectMapper: ObjectMapper = ObjectMapper()

    private val uriController: String = "/api/page"
    @Autowired
    private lateinit var pageDao: PageDao

    @BeforeEach
    fun setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build()
    }

    @Test
    fun test_getPageById() {
        val resultActions = mockMvc.perform(MockMvcRequestBuilders.get("$uriController/1"))
                .andExpect(MockMvcResultMatchers.request().asyncStarted())
                .andReturn()
        mockMvc.perform(MockMvcRequestBuilders.asyncDispatch(resultActions))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().string(Matchers.`is`(Matchers.notNullValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.`is`(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.text", Matchers.`is`("Ulysse")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.image", Matchers.`is`("image3")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nextPageList", Matchers.hasSize<Any?>(3)))
    }

    @Test
    fun test_getFirstPageByStoryId() {
        val resultActions = mockMvc.perform(MockMvcRequestBuilders.get("$uriController/story/2"))
                .andExpect(MockMvcResultMatchers.request().asyncStarted())
                .andReturn()
        mockMvc.perform(MockMvcRequestBuilders.asyncDispatch(resultActions))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().string(Matchers.`is`(Matchers.notNullValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.`is`(4)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.text", Matchers.`is`("Voyage au bout de la nuit est le premier roman de Céline, publié en 1932. Ce livre manqua de deux voix le prix Goncourt mais obtient le prix Renaudot1. Il est traduit en 37 langues2.")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.image", Matchers.`is`("image3")))
    }

    @Test
    fun test_createPage_thenSuccess() {
        val pageDto = TestUtil.createPageDto()
        val resultActions = mockMvc.perform(MockMvcRequestBuilders.put(uriController)
                .content(objectMapper.writeValueAsString(pageDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.request().asyncStarted())
                .andReturn()
        mockMvc.perform(MockMvcRequestBuilders.asyncDispatch(resultActions))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().string(Matchers.`is`(Matchers.notNullValue())))
    }

    @Test
    fun test_createPage_thenAlreadyExists() {
        val pageDto = TestUtil.createPageDto()
        pageDto.id = 1
        val resultActions = mockMvc.perform(MockMvcRequestBuilders.put(uriController)
                .content(objectMapper.writeValueAsString(pageDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.request().asyncStarted())
                .andReturn()
        mockMvc.perform(MockMvcRequestBuilders.asyncDispatch(resultActions))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError)
    }

    @Test
    fun test_deletePage_thenSuccess() {
        var page = TestUtil.createPage()
        page = pageDao.save(page)
        Assertions.assertNotNull(page.id)
        val optionalBefore: Optional<Page> = pageDao.findById(page.id)
        Assertions.assertTrue(optionalBefore.isPresent)

        val resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("$uriController/" + page.id))
                .andExpect(MockMvcResultMatchers.request().asyncStarted())
                .andReturn()
        mockMvc.perform(MockMvcRequestBuilders.asyncDispatch(resultActions))
                .andExpect(MockMvcResultMatchers.status().isOk)

        val optionalAfter: Optional<Page> = pageDao.findById(page.id)
        Assertions.assertTrue(optionalAfter.isEmpty)
    }
}