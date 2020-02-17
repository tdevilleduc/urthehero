package com.tdevilleduc.urthehero.back.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.validation.constraints.NotNull

@Entity
data class Progression(var storyId: Int,
                       var personId: Int,
                       var actualPageId: Int) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: @NotNull Int? = null
}