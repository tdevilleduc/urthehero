package com.tdevilleduc.urthehero.back.dao

import com.tdevilleduc.urthehero.back.model.Progression
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ProgressionDao : JpaRepository<Progression, Int> {
    fun findByUserIdAndStoryId(userId: Int, storyId: Int): Optional<Progression>
    fun findByUserId(userId: Int): MutableList<Progression>
    fun findByStoryId(storyId: Int): MutableList<Progression>
    fun countByStoryId(storyId: Int): Long
}