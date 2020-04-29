package com.tdevilleduc.urthehero.back.service

import com.tdevilleduc.urthehero.back.model.User
import com.tdevilleduc.urthehero.back.service.impl.JwtService
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.*

@ExtendWith(SpringExtension::class)
class JwtServiceTest {
    private val logger: Logger = LoggerFactory.getLogger(JwtServiceTest::class.java)

    private var jwtService  = JwtService()

    @Test
    fun test_generateToken_thenCorrect() {
        val username = "username"
        val user = Mockito.mock(User::class.java)
        `when`(user.username).thenReturn(username)
        val jwt: String = jwtService.generateToken(user)
        Assertions.assertNotNull(jwt)
        logger.info("jwt: {}", jwt)
    }

    @Test
    fun test_validateToken_thenCorrect() {
        val username = "username"
        val user = Mockito.mock(User::class.java)
        `when`(user.username).thenReturn(username)

        val token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(Date(System.currentTimeMillis()))
                .setExpiration(Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, "secret")
                .compact()

        val isValidToken = jwtService.validateToken(token, user)
        Assertions.assertNotNull(isValidToken)
        Assertions.assertTrue(isValidToken)
    }

    @Test
    fun test_validateToken_thenExpiredToken() {
        val username = "username"
        val user = Mockito.mock(User::class.java)
        `when`(user.username).thenReturn(username)

        val token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(Date(System.currentTimeMillis()))
                // expirationDate = now minus 10 hours (which is exactly the valid token delay)
                .setExpiration(Date(System.currentTimeMillis() - 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, "secret")
                .compact()

        assertThrows<ExpiredJwtException> {
            val isValidToken = jwtService.validateToken(token, user)
            Assertions.assertNotNull(isValidToken)
            Assertions.assertFalse(isValidToken)
        }
    }
}