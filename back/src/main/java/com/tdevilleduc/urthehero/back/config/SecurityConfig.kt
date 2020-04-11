package com.tdevilleduc.urthehero.back.config

import com.tdevilleduc.urthehero.back.filter.JwtRequestFilter
import com.tdevilleduc.urthehero.back.service.impl.MyUserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Lazy
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.password.NoOpPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@EnableWebSecurity
class SecurityConfig : WebSecurityConfigurerAdapter() {

    @Autowired @Lazy
    private lateinit var myUserDetailsService: MyUserDetailsService
    @Autowired
    private lateinit var jwtRequestFilter: JwtRequestFilter

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http
                .cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers(*UNAUTHENTICATED_WHITELIST).permitAll()
                .antMatchers(*AUTHORIZED_WHITELIST).authenticated()
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter::class.java)

    }

    @Throws(Exception::class)
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(myUserDetailsService)
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return NoOpPasswordEncoder.getInstance()
    }

    @Bean
    @Throws(Exception::class)
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    companion object {
        private val UNAUTHENTICATED_WHITELIST = arrayOf(
                "/authenticate",
                // -- springDoc requests
                "/swagger-ui/**",
                "/swagger-ui.html",
                "/v3/api-docs/**"
        )

        private val AUTHORIZED_WHITELIST = arrayOf(
                // -- api requests
                "/api/**"
        )
    }

}