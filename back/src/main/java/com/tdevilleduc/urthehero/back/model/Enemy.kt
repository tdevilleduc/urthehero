package com.tdevilleduc.urthehero.back.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id


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