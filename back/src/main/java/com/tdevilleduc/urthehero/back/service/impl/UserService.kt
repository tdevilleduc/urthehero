package com.tdevilleduc.urthehero.back.service.impl

import com.tdevilleduc.urthehero.back.config.Mapper
import com.tdevilleduc.urthehero.back.constant.ApplicationConstants
import com.tdevilleduc.urthehero.back.dao.UserDao
import com.tdevilleduc.urthehero.back.exceptions.UserNotFoundException
import com.tdevilleduc.urthehero.back.model.User
import com.tdevilleduc.urthehero.back.model.UserDTO
import com.tdevilleduc.urthehero.back.service.IUserService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.slf4j.helpers.MessageFormatter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.util.Assert

@Service
class UserService : IUserService {
    val logger: Logger = LoggerFactory.getLogger(UserService::class.java)

    @Autowired
    private lateinit var userDao: UserDao

    override fun exists(userId: Int?): Boolean {
        if (userId == null)
            return false
        val user = userDao.findById(userId)
        if (user.isEmpty) {
            logger.error(ApplicationConstants.ERROR_MESSAGE_USER_DOESNOT_EXIST, userId)
            return false
        }
        return true
    }

    override fun notExists(userId: Int?): Boolean {
        return !exists(userId)
    }

    override fun findById(userId: Int): User {
        val optional = userDao.findById(userId)
        if (optional.isPresent) {
            return optional.get()
        } else {
            throw UserNotFoundException(MessageFormatter.format(ApplicationConstants.ERROR_MESSAGE_USER_DOESNOT_EXIST, userId).message)
        }
    }

    override fun findByUsername(userName: String): User {
        val optional = userDao.findByUsername(userName)
        if (optional.isPresent) {
            return optional.get()
        } else {
            throw UserNotFoundException(MessageFormatter.format(ApplicationConstants.ERROR_MESSAGE_USER_USERNAME_DOESNOT_EXIST, userName).message)
        }
    }

    override fun findAll(): MutableList<User> {
        return userDao.findAll()
    }

    override fun createOrUpdate(userDto: UserDTO): UserDTO {
        val user: User = Mapper.convert(userDto)
        return Mapper.convert(userDao.save(user))
    }

    override fun delete(userId: Int) {
        Assert.notNull(userId, ApplicationConstants.CHECK_USERID_PARAMETER_MANDATORY)
        val user = findById(userId)
        userDao.delete(user)
    }
}