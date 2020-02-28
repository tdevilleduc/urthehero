package com.tdevilleduc.urthehero.back.model

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

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