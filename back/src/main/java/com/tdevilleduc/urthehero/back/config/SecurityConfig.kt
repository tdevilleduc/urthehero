package com.tdevilleduc.urthehero.back.config

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

@Configuration
@EnableWebSecurity
internal class SecurityConfig : WebSecurityConfigurerAdapter() {
    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.authorizeRequests()
                .antMatchers(*AUTH_WHITELIST).permitAll()
                .antMatchers("/**/*").denyAll()
                .and().csrf().disable()
    }

    companion object {
        private val AUTH_WHITELIST = arrayOf( // -- api requests
                "/api/**",  // -- swagger ui
                "/swagger-resources/**",
                "/swagger-ui.html",
                "/v2/api-docs",
                "/webjars/**"
        )
    }
}