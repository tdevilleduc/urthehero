package com.tdevilleduc.urthehero.back.model

import com.fasterxml.jackson.annotation.JsonIgnore
import io.swagger.v3.oas.annotations.media.Schema
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Person(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Schema(description = "\${swagger.model.person.param.storyId}")
        val id: Int,
        @Schema(description = "\${swagger.model.person.param.login}")
        var login: String = "",
        @Schema(description = "\${swagger.model.person.param.displayName}")
        var displayName: String = "",
        @Schema(description = "\${swagger.model.person.param.email}")
        var email: String = "") {

    @JsonIgnore
    var password: String? = null

}