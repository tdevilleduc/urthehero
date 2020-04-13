package com.tdevilleduc.urthehero.back.service

import com.tdevilleduc.urthehero.back.model.Progression
import java.util.*

interface IProgressionService {
    fun doProgressionAction(userId: Int, storyId: Int, newPageId: Int): Progression
    fun findByUserId(userId: Int): MutableList<Progression>
    fun findByUserIdAndStoryId(userId: Int, storyId: Int): Optional<Progression>
    fun countUsersByStoryId(storyId: Int): Long
}