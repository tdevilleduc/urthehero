package com.tdevilleduc.urthehero.back.model

import javax.persistence.*

@Entity
data class Story(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var storyId: Int? = null,
    var authorId: Int? = null,
    var firstPageId: Int? = null,
    var title: String = "",
    var detailedText: String = "",
    var image: String = "",
    @Transient
    var currentPageId: Int? = null,
    @Transient
    var numberOfReaders: Long? = null

)