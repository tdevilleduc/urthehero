package com.tdevilleduc.urthehero.back.service.impl

import com.tdevilleduc.urthehero.back.model.User
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Service
import java.util.*
import java.util.function.Function
import kotlin.collections.HashMap

@Service
class JwtService {

    private val SECRET_KEY: String = "secret"

    fun extractUserName(token: String): String {
        return extractClaim(token, Function { obj: Claims -> obj.subject });
    }

    fun extractExpiration(token: String): Date {
        return extractClaim(token, Function { obj: Claims -> obj.expiration })
    }

    fun <T> extractClaim(token: String, claimsResolver: Function<Claims, T>): T {
        val claims: Claims = extractAllClaims(token)
        return claimsResolver.apply(claims)
    }

    private fun extractAllClaims(token: String): Claims {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).body
    }

    private fun isTokenExpired(token: String): Boolean {
        return extractExpiration(token).before(Date())
    }

    fun generateToken(user: User): String {
        var claims: Map<String, Any> = HashMap()
        return createToken(claims, user.username)
    }

    private fun createToken(claims: Map<String, Any>, subject: String): String {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(Date(System.currentTimeMillis()))
                .setExpiration(Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact()
    }

    fun validateToken(token: String, user: User): Boolean {
        val userName: String = extractUserName(token)
        return userName == user.username && !isTokenExpired(token)
    }
}