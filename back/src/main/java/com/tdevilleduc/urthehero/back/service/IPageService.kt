package com.tdevilleduc.urthehero.back.service

import com.tdevilleduc.urthehero.back.model.Page
import com.tdevilleduc.urthehero.back.model.PageDTO
import java.util.*

interface IPageService {
    fun exists(pageId: Int): Boolean
    fun notExists(pageId: Int): Boolean
    fun findById(pageId: Int): Page
    fun createOrUpdate(page: PageDTO): PageDTO
    fun delete(pageId: Int)
}