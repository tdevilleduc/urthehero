package com.tdevilleduc.urthehero.configserver

import org.springframework.boot.SpringApplication

internal object ConfigServerApplication {
    @JvmStatic
    fun main(args: Array<String>) {
        SpringApplication.run(ConfigServerApplication::class.java, *args)
    }
}