package com.tdevilleduc.urthehero.back.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id


@Entity
data class Enemy(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int,
        var name: String = "",
        var image: String = "",
        var level: Int = 0,
        var lifePoints: Int = 0
)