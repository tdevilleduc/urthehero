package com.tdevilleduc.urthehero.back

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class BackApplication

fun main(args: Array<String>) {
    SpringApplication.run(BackApplication::class.java, *args)
}
