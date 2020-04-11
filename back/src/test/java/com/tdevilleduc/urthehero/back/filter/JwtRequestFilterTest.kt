package com.tdevilleduc.urthehero.back.filter

import com.tdevilleduc.urthehero.back.service.impl.JwtService
import com.tdevilleduc.urthehero.back.service.impl.MyUserDetailsService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.test.util.ReflectionTestUtils
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


internal class JwtRequestFilterTest {

    @Test
    @Throws(ServletException::class, IOException::class)
    fun testDoFilter() {
        val jwt = "toto"
        val userName = "tdevilleduc"

        val filter = JwtRequestFilter()
        val request = mock(HttpServletRequest::class.java)
        val response = mock(HttpServletResponse::class.java)
        val mockFilterChain = mock(FilterChain::class.java)
        val jwtService = mock(JwtService::class.java)
        ReflectionTestUtils.setField(filter, "jwtService", jwtService )
        val myUserDetailsService = mock(MyUserDetailsService::class.java)
        ReflectionTestUtils.setField(filter, "myUserDetailsService", myUserDetailsService)
        val userDetails = mock(UserDetails::class.java)

        `when`(request.getHeader("Authorization")).thenReturn("Bearer $jwt")
        `when`(jwtService.extractUserName(jwt)).thenReturn(userName)
        `when`(myUserDetailsService.loadUserByUsername(userName)).thenReturn(userDetails)
        `when`(jwtService.validateToken(jwt, userDetails)).thenReturn(true)

        filter.doFilter(request, response, mockFilterChain)

        val usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.authorities)
        usernamePasswordAuthenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)

        Assertions.assertEquals(usernamePasswordAuthenticationToken, SecurityContextHolder.getContext().authentication)
        verify(mockFilterChain, times(1)).doFilter(request, response)

    }

}