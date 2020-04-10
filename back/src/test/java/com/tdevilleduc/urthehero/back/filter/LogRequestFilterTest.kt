package com.tdevilleduc.urthehero.back.filter

import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.slf4j.Logger
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

internal class LogRequestFilterTest {

//    @Mock
//    private lateinit var loggerMock: Logger

    @Test
    @Throws(ServletException::class, IOException::class)
    fun testDoFilter() {
        val filter = LogRequestFilter()
        val mockReq = mock(HttpServletRequest::class.java)
        val mockResp = mock(HttpServletResponse::class.java)
        val mockFilterChain = mock(FilterChain::class.java)
        `when`(mockReq.requestURI).thenReturn("/")
//        `when`(filter.logger).thenReturn(loggerMock)

        filter.doFilter(mockReq, mockResp, mockFilterChain)
//        verify(loggerMock, times(1)).isInfoEnabled
    }

//    @Test
//    @Throws(IOException::class, ServletException::class)
//    fun testDoFilterException() {
//        assertThrows(Exception::class.java)
//            {
//                val filter = LogRequestFilter()
//                val mockReq: HttpServletRequest = MockHttpServletRequest()
//                val mockResp: HttpServletResponse = MockHttpServletResponse()
//                val mockFilterChain: FilterChain = mock(FilterChain::class.java)
//
//                filter.doFilter(mockReq, mockResp, mockFilterChain)
//            }
//    }
}