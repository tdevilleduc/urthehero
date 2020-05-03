package com.tdevilleduc.urthehero.back.controller

import com.tdevilleduc.urthehero.back.AbstractITTest
import com.tdevilleduc.urthehero.back.BackApplication
import com.tdevilleduc.urthehero.back.dao.EnemyDao
import com.tdevilleduc.urthehero.back.model.Enemy
import com.tdevilleduc.urthehero.back.util.TestUtil
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.notNullValue
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
internal class EnemyControllerTest : AbstractITTest() {

    private lateinit var mockMvc: MockMvc
    @Autowired
    private lateinit var webApplicationContext: WebApplicationContext

    private val objectMapper: ObjectMapper = ObjectMapper()

    private val uriController: String = "/api/enemy"
    @Autowired
    private lateinit var enemyDao: EnemyDao

    @BeforeEach
    fun setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build()
    }

    @Test
    fun test_getById() {
        val resultActions = mockMvc.perform(MockMvcRequestBuilders.get("$uriController/1"))
                .andExpect(MockMvcResultMatchers.request().asyncStarted())
                .andReturn()
        mockMvc.perform(MockMvcRequestBuilders.asyncDispatch(resultActions))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().string(`is`(notNullValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", `is`(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", `is`("Balrog")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.image", `is`("imageBalrog")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.level", `is`(6)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lifePoints", `is`(25)))
    }

    @Test
    fun test_getByLevel() {
        val resultActions = mockMvc.perform(MockMvcRequestBuilders.get("$uriController/level/1"))
                .andExpect(MockMvcResultMatchers.request().asyncStarted())
                .andReturn()
        mockMvc.perform(MockMvcRequestBuilders.asyncDispatch(resultActions))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().string(`is`(notNullValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.level", `is`(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", `is`(notNullValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", `is`(notNullValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.image", `is`(notNullValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lifePoints", `is`(notNullValue())))
    }

    @Test
    fun test_create_thenSuccess() {
        val enemyDto = TestUtil.createEnemyDto()
        val resultActions = mockMvc.perform(MockMvcRequestBuilders.put(uriController)
                .content(objectMapper.writeValueAsString(enemyDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.request().asyncStarted())
                .andReturn()
        mockMvc.perform(MockMvcRequestBuilders.asyncDispatch(resultActions))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().string(`is`(notNullValue())))
    }

    @Test
    fun test_create_thenAlreadyExists() {
        val enemyDto = TestUtil.createEnemyDto()
        enemyDto.id = 1
        val resultActions = mockMvc.perform(MockMvcRequestBuilders.put(uriController)
                .content(objectMapper.writeValueAsString(enemyDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.request().asyncStarted())
                .andReturn()
        mockMvc.perform(MockMvcRequestBuilders.asyncDispatch(resultActions))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError)
    }

    @Test
    fun test_delete_thenSuccess() {
        var enemy = TestUtil.createEnemy()
        enemy = enemyDao.save(enemy)
        Assertions.assertNotNull(enemy.id)
        val optionalBefore: Optional<Enemy> = enemyDao.findById(enemy.id)
        Assertions.assertTrue(optionalBefore.isPresent)

        val resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("$uriController/" + enemy.id))
                .andExpect(MockMvcResultMatchers.request().asyncStarted())
                .andReturn()
        mockMvc.perform(MockMvcRequestBuilders.asyncDispatch(resultActions))
                .andExpect(MockMvcResultMatchers.status().isOk)

        val optionalAfter: Optional<Enemy> = enemyDao.findById(enemy.id)
        Assertions.assertTrue(optionalAfter.isEmpty)
    }
}