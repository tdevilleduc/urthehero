package com.tdevilleduc.urthehero.back.filter

import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

internal class LogRequestFilterTest {

    @Test
    @Throws(ServletException::class, IOException::class)
    fun testDoFilter() {
        val filter = LogRequestFilter()
        val mockReq = mock(HttpServletRequest::class.java)
        val mockResp = mock(HttpServletResponse::class.java)
        val mockFilterChain = mock(FilterChain::class.java)
        `when`(mockReq.requestURI).thenReturn("/")

        filter.doFilter(mockReq, mockResp, mockFilterChain)
    }

}