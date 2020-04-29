package com.tdevilleduc.urthehero.back.controller

import com.tdevilleduc.urthehero.back.model.AuthenticationRequest
import com.tdevilleduc.urthehero.back.model.AuthenticationResponse
import com.tdevilleduc.urthehero.back.model.User
import com.tdevilleduc.urthehero.back.service.impl.JwtService
import com.tdevilleduc.urthehero.back.service.impl.MyUserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.*
import java.util.concurrent.Callable

@RestController
internal class AuthenticationController {

    @Autowired
    private lateinit var authenticationManager: AuthenticationManager
    @Autowired
    private lateinit var myUserDetailsService: MyUserDetailsService
    @Autowired
    private lateinit var jwtService: JwtService

    @RequestMapping(value = ["/authenticate"], method = [RequestMethod.POST])
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    fun createAuthenticationToken(@RequestBody authenticationRequest: AuthenticationRequest): Callable<ResponseEntity<AuthenticationResponse>> = Callable {
        try {
            authenticationManager.authenticate(UsernamePasswordAuthenticationToken(authenticationRequest.username, authenticationRequest.password))
        } catch (e: BadCredentialsException) {
            throw Exception("Incorrect username or password", e)
        }
        val user: User = myUserDetailsService.loadUserByUsername(authenticationRequest.username)
        val jwt = jwtService.generateToken(user)
        return@Callable ResponseEntity.ok(AuthenticationResponse(jwt))
    }
}