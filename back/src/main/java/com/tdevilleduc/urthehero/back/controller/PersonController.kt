package com.tdevilleduc.urthehero.back.controller

import com.tdevilleduc.urthehero.back.constant.ApplicationConstants
import com.tdevilleduc.urthehero.back.exceptions.UserInternalErrorException
import com.tdevilleduc.urthehero.back.exceptions.UserNotFoundException
import com.tdevilleduc.urthehero.back.model.User
import com.tdevilleduc.urthehero.back.model.UserDTO
import com.tdevilleduc.urthehero.back.service.IUserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.helpers.MessageFormatter
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.util.Assert
import org.springframework.web.bind.annotation.*
import java.util.concurrent.Callable

@Tag(name = "User", description = "User Controller")
@RestController
@RequestMapping("/api/user")
internal class PersonController(private val userService: IUserService) {

    @GetMapping(value = ["/all"], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "\${swagger.controller.user.get-all.value}", description = "\${swagger.controller.user.get-all.notes}")
    @ResponseBody
    fun getUsers(): Callable<ResponseEntity<MutableList<User>>> = Callable {
        ResponseEntity.ok(userService.findAll())
    }

    @GetMapping(value = ["/{userId}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "\${swagger.controller.user.get-by-id.value}", description = "\${swagger.controller.user.get-by-id.notes}")
    @ResponseBody
    fun getUserById(@PathVariable userId: Int): Callable<ResponseEntity<User>> = Callable {
        ResponseEntity.ok(userService.findById(userId))
    }

    @PutMapping
    @ResponseBody
    fun createUser(@RequestBody userDto: UserDTO): Callable<ResponseEntity<UserDTO>> = Callable {
        if (userService.exists(userDto.userId)) {
            throw UserInternalErrorException(MessageFormatter.format(ApplicationConstants.ERROR_MESSAGE_USER_USERID_ALREADY_EXISTS, userDto.userId).message)
        }
        ResponseEntity.ok(userService.createOrUpdate(userDto))
    }

    @PostMapping
    @ResponseBody
    fun updateUser(@RequestBody userDto: UserDTO): Callable<ResponseEntity<UserDTO>> = Callable {
        Assert.notNull(userDto.userId) { throw UserInternalErrorException(ApplicationConstants.ERROR_MESSAGE_USER_USERID_CANNOT_BE_NULL) }
        if (userService.notExists(userDto.userId)) {
            throw UserInternalErrorException(MessageFormatter.format(ApplicationConstants.ERROR_MESSAGE_USER_DOESNOT_EXIST, userDto.userId).message)
        }
        ResponseEntity.ok(userService.createOrUpdate(userDto))
    }

    @DeleteMapping(value = ["/{userId}"])
    @ResponseBody
    fun deleteUser(@PathVariable userId: Int) = Callable {
        userService.delete(userId)
    }

}