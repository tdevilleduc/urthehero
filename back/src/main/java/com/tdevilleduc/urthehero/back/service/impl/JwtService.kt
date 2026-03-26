package com.tdevilleduc.urthehero.back.service.impl

import com.tdevilleduc.urthehero.back.model.User
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Service
import java.util.*
import java.util.function.Function
import javax.crypto.SecretKey

@Service
class JwtService {

    private val secretKey: SecretKey = Keys.hmacShaKeyFor(
        "secret-key-must-be-at-least-32-bytes!!".toByteArray()
    )

    fun extractUserName(token: String): String {
        return extractClaim(token, Function { obj: Claims -> obj.subject })
    }

    fun extractExpiration(token: String): Date {
        return extractClaim(token, Function { obj: Claims -> obj.expiration })
    }

    fun <T> extractClaim(token: String, claimsResolver: Function<Claims, T>): T {
        val claims: Claims = extractAllClaims(token)
        return claimsResolver.apply(claims)
    }

    private fun extractAllClaims(token: String): Claims {
        return Jwts.parser()
            .verifyWith(secretKey)
            .build()
            .parseSignedClaims(token)
            .payload
    }

    private fun isTokenExpired(token: String): Boolean {
        return extractExpiration(token).before(Date())
    }

    fun generateToken(user: User): String {
        return createToken(emptyMap(), user.username)
    }

    private fun createToken(claims: Map<String, Any>, subject: String): String {
        return Jwts.builder()
            .claims(claims)
            .subject(subject)
            .issuedAt(Date(System.currentTimeMillis()))
            .expiration(Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
            .signWith(secretKey)
            .compact()
    }

    fun validateToken(token: String, user: User): Boolean {
        val userName: String = extractUserName(token)
        return userName == user.username && !isTokenExpired(token)
    }
}
