package com.tdevilleduc.urthehero.back.service

import com.tdevilleduc.urthehero.back.model.User
import com.tdevilleduc.urthehero.back.model.UserDTO

interface IUserService {
    fun exists(userId: Int?): Boolean
    fun notExists(userId: Int?): Boolean
    fun findById(userId: Int): User
    fun findByUsername(userName: String): User
    fun findAll(): MutableList<User>
    fun createOrUpdate(userDto: UserDTO): UserDTO
    fun delete(userId: Int)
}