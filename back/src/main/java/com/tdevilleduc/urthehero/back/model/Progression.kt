package com.tdevilleduc.urthehero.back.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.validation.constraints.NotNull

@Entity
data class Progression(var storyId: Int,
                       var userId: Int,
                       var actualPageId: Int) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: @NotNull Int? = null
}