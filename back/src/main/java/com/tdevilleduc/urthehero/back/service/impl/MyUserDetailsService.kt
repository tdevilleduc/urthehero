package com.tdevilleduc.urthehero.back.service.impl

import com.tdevilleduc.urthehero.back.model.User
import com.tdevilleduc.urthehero.back.service.IUserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class MyUserDetailsService : UserDetailsService {

    @Autowired
    private lateinit var userService: IUserService

    override fun loadUserByUsername(userName: String): User {
        return userService.findByUsername(userName)
    }
}