package com.tdevilleduc.urthehero.back.service

import com.tdevilleduc.urthehero.back.AbstractTest
import com.tdevilleduc.urthehero.back.BackApplication
import com.tdevilleduc.urthehero.back.dao.UserDao
import com.tdevilleduc.urthehero.back.exceptions.UserNotFoundException
import com.tdevilleduc.urthehero.back.model.User
import com.tdevilleduc.urthehero.back.service.impl.UserService
import com.tdevilleduc.urthehero.back.util.TestUtil
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.function.Executable
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.*

@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [BackApplication::class])
internal class PersonServiceTest : AbstractTest() {
    @Autowired
    private lateinit var userService: UserService
    @Autowired
    private lateinit var userDao: UserDao

    @Test
    fun test_exists_thenCorrect() {
        val userId = 1
        val exists = userService.exists(userId)
        Assertions.assertTrue(exists)
    }

    @Test
    fun test_exists_withIdNull() {
        val userId = null
        val exists = userService.exists(userId)
        Assertions.assertFalse(exists)
    }

    @Test
    fun test_notExists_thenCorrect() {
        val userId = 41
        val notExists = userService.notExists(userId)
        Assertions.assertTrue(notExists)
    }

    @Test
    fun test_findById_thenCorrect() {
        val userId = 1
        val user = userService.findById(userId)
        Assertions.assertNotNull(user)
        Assertions.assertEquals(1, user.userId)
        Assertions.assertEquals("tdevilleduc", user.username)
        Assertions.assertEquals("password", user.password)
    }

    @Test
    fun test_findById_thenNotFound() {
        val userId = 13
        Assertions.assertThrows(UserNotFoundException::class.java, Executable { userService.findById(userId) } )
    }

    @Test
    fun delete_thenNotFound() {
        val userId = 13
        Assertions.assertThrows(UserNotFoundException::class.java, Executable { userService.delete(userId) })
    }

    @Test
    fun test_findByUsername_thenCorrect() {
        val userUsername = "mgianesini"
        val user = userService.findByUsername(userUsername)
        Assertions.assertNotNull(user)
        Assertions.assertEquals(2, user.userId)
        Assertions.assertEquals("mgianesini", user.username)
        Assertions.assertEquals("password", user.password)
    }

    @Test
    fun test_findByUsername_thenNotFound() {
        val userUsername = "toto"
        Assertions.assertThrows(UserNotFoundException::class.java) {
            userService.findByUsername(userUsername)
        }
    }

    @Test
    fun test_delete_thenSuccess() {
        var user = TestUtil.createUser()
        user = userDao.save(user)
        Assertions.assertNotNull(user.userId)
        val optionalBefore: Optional<User> = userDao.findById(user.userId!!)
        Assertions.assertTrue(optionalBefore.isPresent)

        // delete user entity
        userService.delete(user.userId!!)

        val optionalAfter: Optional<User> = userDao.findById(user.userId!!)
        Assertions.assertTrue(optionalAfter.isEmpty)
    }
}