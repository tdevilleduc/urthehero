package com.tdevilleduc.urthehero.back.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
data class NextPage(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @JsonIgnore
        var id: Int? = null,
        var text: String = "",
        var pageId: Int = 0,
        var destinationPageId: Int = 0,
        var position: Position? = null

)