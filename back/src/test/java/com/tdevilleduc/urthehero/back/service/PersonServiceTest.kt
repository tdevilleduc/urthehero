package com.tdevilleduc.urthehero.back.service

import com.tdevilleduc.urthehero.back.dao.UserDao
import com.tdevilleduc.urthehero.back.exceptions.UserNotFoundException
import com.tdevilleduc.urthehero.back.service.impl.UserService
import com.tdevilleduc.urthehero.back.util.TestUtil
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.testcontainers.shaded.org.apache.commons.lang.RandomStringUtils
import java.util.*

@ExtendWith(MockitoExtension::class)
internal class PersonServiceTest {
    private val random: Random = Random()

    @InjectMocks
    private lateinit var userService: UserService
    @Mock
    private lateinit var userDao: UserDao

    @Test
    fun test_exists_withIdNull() {
        val userId = null
        val exists = userService.exists(userId)
        Assertions.assertFalse(exists)
    }

    @Test
    fun test_notExists_thenSuccess() {
        val userId = random.nextInt()
        Mockito.`when`(userDao.findById(userId)).thenReturn(Optional.empty())

        val notExists = userService.notExists(userId)
        Assertions.assertTrue(notExists)
    }

    @Test
    fun test_findById_thenCorrect() {
        val expectedUser = TestUtil.createUser()
        val userId = random.nextInt()
        expectedUser.userId = userId
        Mockito.`when`(userDao.findById(expectedUser.userId!!)).thenReturn(Optional.of(expectedUser))

        val user = userService.findById(expectedUser.userId!!)
        Assertions.assertNotNull(user)
        Assertions.assertEquals(userId, user.userId)
        Assertions.assertEquals(expectedUser.username, user.username)
        Assertions.assertEquals(expectedUser.password, user.password)
    }
    @Test
    fun test_findById_thenNotFound() {
        val userId = random.nextInt()
        Mockito.`when`(userDao.findById(userId)).thenReturn(Optional.empty())

        Assertions.assertThrows(UserNotFoundException::class.java) { userService.findById(userId) }
    }

    @Test
    fun delete_thenNotFound() {
        val userId = random.nextInt()
        Mockito.`when`(userDao.findById(userId)).thenReturn(Optional.empty())

        Assertions.assertThrows(UserNotFoundException::class.java) { userService.delete(userId) }
    }

    @Test
    fun test_findByUsername_thenCorrect() {
        val expectedUser = TestUtil.createUser()
        Mockito.`when`(userDao.findByUsername(expectedUser.username)).thenReturn(Optional.of(expectedUser))

        val user = userService.findByUsername(expectedUser.username)
        Assertions.assertNotNull(user)
        Assertions.assertEquals(expectedUser.userId, user.userId)
        Assertions.assertEquals(expectedUser.username, user.username)
        Assertions.assertEquals(expectedUser.password, user.password)
    }

    @Test
    fun test_findByUsername_thenNotFound() {
        val userUsername = RandomStringUtils.random(20)
        Mockito.`when`(userDao.findByUsername(userUsername)).thenReturn(Optional.empty())

        Assertions.assertThrows(UserNotFoundException::class.java) {
            userService.findByUsername(userUsername)
        }
    }
}