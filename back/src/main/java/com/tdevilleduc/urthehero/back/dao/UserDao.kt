package com.tdevilleduc.urthehero.back.dao

import com.tdevilleduc.urthehero.back.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserDao : JpaRepository<User, Int> {
    fun findByUsername(userName: String): Optional<User>
}