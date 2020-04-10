package com.tdevilleduc.urthehero.back.service

import com.tdevilleduc.urthehero.back.AbstractTest
import com.tdevilleduc.urthehero.back.BackApplication
import com.tdevilleduc.urthehero.back.exceptions.PersonNotFoundException
import com.tdevilleduc.urthehero.back.service.impl.PersonService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.function.Executable
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [BackApplication::class])
internal class PersonServiceTest : AbstractTest() {
    @Autowired
    private lateinit var personService: PersonService

    @Test
    fun test_exists_thenCorrect() {
        val personId = 1
        val exists = personService.exists(personId)
        Assertions.assertTrue(exists)
    }

    @Test
    fun test_notExists_thenCorrect() {
        val personId = 41
        val notExists = personService.notExists(personId)
        Assertions.assertTrue(notExists)
    }

    @Test
    fun test_findById_thenCorrect() {
        val personId = 1
        val person = personService.findById(personId)
        Assertions.assertNotNull(person)
        Assertions.assertEquals(1, person.id)
        Assertions.assertEquals("Thomas Deville-Duc", person.displayName)
        Assertions.assertEquals("thomas@gmail.com", person.email)
        Assertions.assertEquals("tdevilleduc", person.login)
        Assertions.assertEquals("password", person.password)
    }

    @Test
    fun test_findById_thenNotFound() {
        val personId = 13
        Assertions.assertThrows(PersonNotFoundException::class.java, Executable { personService.findById(personId) } )
    }

    @Test
    fun delete_thenNotFound() {
        val personId = 13
        Assertions.assertThrows(PersonNotFoundException::class.java, Executable { personService.delete(personId) })
    }
}