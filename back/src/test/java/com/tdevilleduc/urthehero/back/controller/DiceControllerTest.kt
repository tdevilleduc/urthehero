package com.tdevilleduc.urthehero.back.controller

import com.tdevilleduc.urthehero.back.AbstractITTest
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
internal class DiceControllerTest : AbstractITTest() {
    private lateinit var mockMvc: MockMvc
    @Autowired
    private lateinit var webApplicationContext: WebApplicationContext

    private val uriController: String? = "/api/dice"

    @BeforeEach
    fun setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build()
    }

    @Test
    @Throws(Exception::class)
    fun test_rollOne_thenSuccess() {
        val diceString = "DE_20"
        val resultActions = mockMvc.perform(MockMvcRequestBuilders.get("$uriController/roll/$diceString"))
                .andExpect(MockMvcResultMatchers.request().asyncStarted())
                .andReturn()
        mockMvc.perform(MockMvcRequestBuilders.asyncDispatch(resultActions))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().string(Matchers.`is`(Matchers.notNullValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.value", Matchers.isA<Any?>(Int::class.java)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.value", Matchers.greaterThan<Int>(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.value", Matchers.lessThanOrEqualTo<Int>(20)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dice", Matchers.`is`(diceString)))
    }

    @Test
    @Throws(Exception::class)
    fun test_rollMulti_thenSuccess() {
        val diceString = "DE_20"
        val numberOfRolls = 4
        val resultActions = mockMvc.perform(MockMvcRequestBuilders.get("$uriController/roll/$diceString/$numberOfRolls"))
                .andExpect(MockMvcResultMatchers.request().asyncStarted())
                .andReturn()
        mockMvc.perform(MockMvcRequestBuilders.asyncDispatch(resultActions))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().string(Matchers.`is`(Matchers.notNullValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize<Any?>(numberOfRolls)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].value", Matchers.isA<Any?>(Int::class.java)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].value", Matchers.greaterThan<Int>(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].value", Matchers.lessThanOrEqualTo<Int>(20)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].dice", Matchers.`is`(diceString)))
    }
}