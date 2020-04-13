package com.tdevilleduc.urthehero.back.controller

import com.tdevilleduc.urthehero.back.AbstractTest
import com.tdevilleduc.urthehero.back.BackApplication
import com.tdevilleduc.urthehero.back.config.Mapper
import com.tdevilleduc.urthehero.back.dao.UserDao
import com.tdevilleduc.urthehero.back.model.User
import com.tdevilleduc.urthehero.back.service.impl.UserService
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
internal class PersonControllerTest : AbstractTest() {

    private lateinit var mockMvc: MockMvc
    @Autowired
    private lateinit var webApplicationContext: WebApplicationContext
    @Autowired
    private lateinit var userDao: UserDao
    @Autowired
    private lateinit var userService: UserService

    private val objectMapper: ObjectMapper = ObjectMapper()

    private val uriController: String = "/api/user"

    @BeforeEach
    fun setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build()
    }

    @Test
    fun test_getAllUsers_thenSuccess() {
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
    fun test_getUserById_thenSuccess() {
        val resultActions = mockMvc.perform(MockMvcRequestBuilders.get("$uriController/2"))
                .andExpect(MockMvcResultMatchers.request().asyncStarted())
                .andReturn()
        mockMvc.perform(MockMvcRequestBuilders.asyncDispatch(resultActions))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().string(Matchers.`is`(Matchers.notNullValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId", Matchers.`is`(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username", Matchers.`is`("mgianesini")))
    }

    @Test
    fun test_createUser_thenSuccess() {
        val userDto = TestUtil.createUserDto()
        val resultActions = mockMvc.perform(MockMvcRequestBuilders.put(uriController)
                .content(objectMapper.writeValueAsString(userDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.request().asyncStarted())
                .andReturn()
        mockMvc.perform(MockMvcRequestBuilders.asyncDispatch(resultActions))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().string(Matchers.`is`(Matchers.notNullValue())))

         // clean database
        userService.delete(4)
    }

    @Test
    fun test_deleteUser_thenSuccess() {
        var user = TestUtil.createUser()
        user = userDao.save(user)
        Assertions.assertNotNull(user.userId)
        val optionalBefore: Optional<User> = userDao.findById(user.userId!!)
        Assertions.assertTrue(optionalBefore.isPresent)

        val resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("$uriController/" + user.userId))
                .andExpect(MockMvcResultMatchers.request().asyncStarted())
                .andReturn()
        mockMvc.perform(MockMvcRequestBuilders.asyncDispatch(resultActions))
                .andExpect(MockMvcResultMatchers.status().isOk)

        val optionalAfter: Optional<User> = userDao.findById(user.userId!!)
        Assertions.assertTrue(optionalAfter.isEmpty)
    }
}