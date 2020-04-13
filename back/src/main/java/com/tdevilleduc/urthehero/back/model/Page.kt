package com.tdevilleduc.urthehero.back.model

import io.swagger.v3.oas.annotations.media.Schema
import javax.persistence.*

@Entity
class Page(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Schema(description = "\${swagger.model.page.param.storyId}")
        val id: Int,
        var text: String = "",
        var image: String = "",
        var storyId: Int? = null
) {

    @Transient
    var nextPageList: MutableList<NextPage> = emptyList<NextPage>().toMutableList()

}