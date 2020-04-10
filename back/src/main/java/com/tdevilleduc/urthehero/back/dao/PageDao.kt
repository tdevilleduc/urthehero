package com.tdevilleduc.urthehero.back.dao

import com.tdevilleduc.urthehero.back.model.Page
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PageDao : JpaRepository<Page, Int> {
    fun countByStoryId(storyId: Int): Long
}