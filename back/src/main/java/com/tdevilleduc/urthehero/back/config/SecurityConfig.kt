package com.tdevilleduc.urthehero.back.config

import com.tdevilleduc.urthehero.back.service.impl.MyUserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.password.NoOpPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@EnableWebSecurity
internal class SecurityConfig : WebSecurityConfigurerAdapter() {

    @Autowired
    private lateinit var myUserDetailsService: MyUserDetailsService

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.authorizeRequests()
                .antMatchers(*AUTHENTICATED_WHITELIST).permitAll()
                .antMatchers(*AUTHORIZED_WHITELIST).authenticated()
                .anyRequest().denyAll()
                .and().formLogin()
//                .and().httpBasic()
                .and().csrf().disable()
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(myUserDetailsService)
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return NoOpPasswordEncoder.getInstance()
    }

    companion object {
        private val AUTHENTICATED_WHITELIST = arrayOf(
                "/login"
        )

        private val AUTHORIZED_WHITELIST = arrayOf(
                // -- api requests
                "/api/**",
                // -- springDoc requests
                "/swagger-ui/**",
                "/swagger-ui.html",
                "/v3/api-docs/**"
        )
    }
}