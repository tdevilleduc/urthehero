package com.tdevilleduc.urthehero.back.model


class UserDTO (
        var userId: Int? = null,
        var username: String = "",
        var password: String = "",
        var role: String = "ROLE_USER"
)