package com.tdevilleduc.urthehero.back.model

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.testcontainers.shaded.org.apache.commons.lang.RandomStringUtils

internal class PersonTest {
    @Test
    fun test_Constructor() {
        val personId = 31
        val personLogin = RandomStringUtils.random(20)
        val personDisplayName = RandomStringUtils.random(20)
        val personEmail = RandomStringUtils.random(20)
        val person = Person(personId, personLogin, personDisplayName, personEmail)
        Assertions.assertEquals(person.login, personLogin)
        Assertions.assertEquals(person.displayName, personDisplayName)
        Assertions.assertEquals(person.email, personEmail)
        val secondPerson = Person(personId)
        secondPerson.login = personLogin
        secondPerson.displayName = personDisplayName
        secondPerson.email = personEmail
        Assertions.assertEquals(secondPerson.toString(), person.toString())
        Assertions.assertEquals(secondPerson, person)
    }
}