package com.tdevilleduc.urthehero.back.service

import com.tdevilleduc.urthehero.back.model.Person
import com.tdevilleduc.urthehero.back.model.PersonDTO

interface IPersonService {
    fun exists(personId: Int?): Boolean
    fun notExists(personId: Int?): Boolean
    fun findById(personId: Int): Person
    fun findByLogin(userName: String): Person
    fun findAll(): MutableList<Person>
    fun createOrUpdate(personDto: PersonDTO): PersonDTO
    fun delete(personId: Int)
}