package com.tdevilleduc.urthehero.back.model

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.apache.commons.lang3.RandomStringUtils

internal class PersonTest {
    @Test
    fun test_defaultRole_isRoleUser() {
        val user = User(null, "username", "password")
        Assertions.assertEquals("ROLE_USER", user.getRole())
    }

    @Test
    fun test_adminRole() {
        val user = User(null, "username", "password", "ROLE_ADMIN")
        Assertions.assertEquals("ROLE_ADMIN", user.getRole())
        Assertions.assertEquals(1, user.authorities.size)
        Assertions.assertEquals("ROLE_ADMIN", user.authorities.first().authority)
    }

    @Test
    fun test_setRole() {
        val user = User(null, "username", "password")
        user.setRole("ROLE_ADMIN")
        Assertions.assertEquals("ROLE_ADMIN", user.getRole())
    }
}
