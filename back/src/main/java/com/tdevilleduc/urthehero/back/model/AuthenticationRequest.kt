package com.tdevilleduc.urthehero.back.model

import java.io.Serializable

class AuthenticationRequest : Serializable {
    lateinit var username: String
    lateinit var password: String

    constructor() {}
    constructor(username: String, password: String) {
        this.username = username
        this.password = password
    }
}