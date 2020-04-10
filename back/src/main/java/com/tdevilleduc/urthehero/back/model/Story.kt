package com.tdevilleduc.urthehero.back.model

import io.swagger.v3.oas.annotations.media.Schema
import javax.persistence.*

@Entity
data class Story(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "\${swagger.model.story.param.storyId}")
    var storyId: Int? = null,
    @Schema(description = "\${swagger.model.story.param.authorId}")
    var authorId: Int? = null,
    @Schema(description = "\${swagger.model.story.param.firstPageId}")
    var firstPageId: Int? = null,
    @Schema(description = "\${swagger.model.story.param.title}")
    var title: String = "",
    @Schema(description = "\${swagger.model.story.param.detailedText}")
    var detailedText: String = "",
    @Schema(description = "\${swagger.model.story.param.image}")
    var image: String = "",
    @Transient
    @Schema(description = "\${swagger.model.story.param.numberOfReaders}")
    var numberOfReaders: Long? = null,
    @Transient
    @Schema(description = "\${swagger.model.story.param.numberOfPages}")
    var numberOfPages: Long? = null

)