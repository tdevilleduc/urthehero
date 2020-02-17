package com.tdevilleduc.urthehero.back.model

import javax.persistence.*

@Entity
class Page(var text: String = "",
           var image: String = "") {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null
    @Transient
    var nextPageList: MutableList<NextPage> = emptyList<NextPage>().toMutableList()

}