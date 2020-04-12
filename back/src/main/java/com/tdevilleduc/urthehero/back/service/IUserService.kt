package com.tdevilleduc.urthehero.back.service

import com.tdevilleduc.urthehero.back.model.User

interface IUserService {
    fun exists(userId: Int): Boolean
    fun notExists(userId: Int): Boolean
    fun findById(userId: Int): User
    fun findByUsername(userName: String): User
    fun findAll(): MutableList<User>
}