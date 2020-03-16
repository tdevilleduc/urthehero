package com.tdevilleduc.urthehero.back.controller

import com.tdevilleduc.urthehero.back.AbstractTest
import com.tdevilleduc.urthehero.back.BackApplication
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [BackApplication::class])
@WebAppConfiguration
internal class DiceControllerTest : AbstractTest() {
    private lateinit var mockMvc: MockMvc
    @Autowired
    private lateinit var webTestClient: WebTestClient

    private val uriController: String? = "/api/dice"

    @Test
    @Throws(Exception::class)
    fun test_rollOne_thenSuccess() {
        val diceString = "DE_20"

        webTestClient.get()
                .uri("$uriController/roll/$diceString")
                .exchange()
                .expectStatus().isOk
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.value").isEqualTo(Matchers.isA<Any?>(Int::class.java))
                .jsonPath("$.value").isEqualTo(Matchers.greaterThan<Int>(0))
                .jsonPath("$.value").isEqualTo(Matchers.lessThanOrEqualTo<Int>(20))
                .jsonPath("$.dice").isEqualTo(Matchers.`is`(diceString))
    }

    @Test
    @Throws(Exception::class)
    fun test_rollMulti_thenSuccess() {
        val diceString = "DE_20"
        val numberOfRolls = 4

        webTestClient.get()
                .uri("$uriController/roll/$diceString/$numberOfRolls")
                .exchange()
                .expectStatus().isOk
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$").isEqualTo(Matchers.hasSize<Any?>(numberOfRolls))
                .jsonPath("$.value").isEqualTo(Matchers.isA<Any?>(Int::class.java))
                .jsonPath("$.value").isEqualTo(Matchers.greaterThan<Int>(0))
                .jsonPath("$.value").isEqualTo(Matchers.lessThanOrEqualTo<Int>(20))
                .jsonPath("$.dice").isEqualTo(Matchers.`is`(diceString))
    }
}