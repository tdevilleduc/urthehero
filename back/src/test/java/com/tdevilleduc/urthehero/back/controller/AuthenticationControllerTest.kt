package com.tdevilleduc.urthehero.back.controller

import com.tdevilleduc.urthehero.back.AbstractITTest
import com.tdevilleduc.urthehero.back.BackApplication
import com.tdevilleduc.urthehero.back.model.AuthenticationRequest
import com.tdevilleduc.urthehero.back.util.JsonUtil
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext


@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [BackApplication::class])
@WebAppConfiguration
class AuthenticationControllerTest : AbstractITTest() {
    private lateinit var mockMvc: MockMvc
    @Autowired
    private lateinit var webApplicationContext: WebApplicationContext

    private val uriController: String = "/authenticate"

    @BeforeEach
    fun setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build()
    }

    @Test
    @Throws(Exception::class)
    fun test_createAuthenticationToken_thenSuccess() {
        val username = "tdevilleduc"
        val password = "password"
        val authenticationRequest = AuthenticationRequest(username, password)
        val resultActions = mockMvc.perform(post(uriController)
                .content(JsonUtil.asJsonString(authenticationRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(request().asyncStarted())
                .andReturn()
        mockMvc.perform(asyncDispatch(resultActions))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk)
                .andExpect(content().string(`is`(notNullValue())))
                .andExpect(jsonPath("$.jwt", isA<Any>(String::class.java)))
    }

    @Test
    @Throws(Exception::class)
    fun test_createAuthenticationToken_thenBadCredential() {
        val username = "coin"
        val password = "password"
        val authenticationRequest = AuthenticationRequest(username, password)
        val resultActions = mockMvc.perform(post(uriController)
                .content(JsonUtil.asJsonString(authenticationRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(request().asyncStarted())
                .andReturn()
        mockMvc.perform(asyncDispatch(resultActions))
                .andExpect(status().isNotFound)
    }
}