package com.tdevilleduc.urthehero.back.service

import com.tdevilleduc.urthehero.back.AbstractITTest
import com.tdevilleduc.urthehero.back.BackApplication
import com.tdevilleduc.urthehero.back.dao.PageDao
import com.tdevilleduc.urthehero.back.dao.UserDao
import com.tdevilleduc.urthehero.back.model.User
import com.tdevilleduc.urthehero.back.util.TestUtil
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.*


@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [BackApplication::class])
internal class ITPersonServiceTest : AbstractITTest() {

    @Autowired
    private lateinit var userService: IUserService

    @Autowired
    private lateinit var userDao: UserDao


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