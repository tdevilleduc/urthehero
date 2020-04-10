package com.tdevilleduc.urthehero.back.filter

import com.tdevilleduc.urthehero.back.constant.ApplicationConstants
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.io.IOException
import javax.servlet.*
import javax.servlet.http.HttpServletRequest

@Component
class LogRequestFilter : Filter {

    val logger: Logger = LoggerFactory.getLogger(LogRequestFilter::class.java)

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(servletRequest: ServletRequest, servletResponse: ServletResponse, filterChain: FilterChain) {

        if (logger.isInfoEnabled) {
            val req = servletRequest as HttpServletRequest;
            logger.info(ApplicationConstants.CONTROLLER_CALL_LOG, req.requestURI)
        }
        filterChain.doFilter(servletRequest, servletResponse)
    }
}