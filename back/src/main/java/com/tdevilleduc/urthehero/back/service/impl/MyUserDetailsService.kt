package com.tdevilleduc.urthehero.back.service.impl

import com.tdevilleduc.urthehero.back.service.IPersonService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class MyUserDetailsService : UserDetailsService {

    @Autowired
    private lateinit var personService: IPersonService

    override fun loadUserByUsername(userName: String): UserDetails {
        val person = personService.findByLogin(userName)
        return User(person.login, person.password, listOf(SimpleGrantedAuthority("ADMIN")))
    }
}