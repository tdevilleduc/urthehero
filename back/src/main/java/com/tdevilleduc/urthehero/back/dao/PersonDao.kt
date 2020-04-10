package com.tdevilleduc.urthehero.back.dao

import com.tdevilleduc.urthehero.back.model.Person
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface PersonDao : JpaRepository<Person, Int> {
    fun findByLogin(userName: String): Optional<Person>
}