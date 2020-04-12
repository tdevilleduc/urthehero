package com.tdevilleduc.urthehero.back.filter

import com.tdevilleduc.urthehero.back.service.impl.JwtService
import com.tdevilleduc.urthehero.back.service.impl.MyUserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import java.io.IOException
import javax.servlet.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Component
class JwtRequestFilter : Filter {

    @Autowired
    private lateinit var myUserDetailsService: MyUserDetailsService
    @Autowired
    private lateinit var jwtService: JwtService

    @Throws(ServletException::class, IOException::class)
    override fun doFilter(servletRequest: ServletRequest?, servletResponse: ServletResponse?, chain: FilterChain?) {
        val request = servletRequest as HttpServletRequest
        val response = servletResponse as HttpServletResponse

        val authorizationHeader = request.getHeader("Authorization")
        var userName: String? = null
        var jwt: String? = null
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7)
            userName = jwtService.extractUserName(jwt)
        }
        if (userName != null && SecurityContextHolder.getContext().authentication == null) {
            val user = myUserDetailsService.loadUserByUsername(userName)
            if (jwtService.validateToken(jwt!!, user)) {
                val usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(
                        user, null, user.authorities)
                usernamePasswordAuthenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = usernamePasswordAuthenticationToken
            }
        }
        chain!!.doFilter(request, response)
    }
}