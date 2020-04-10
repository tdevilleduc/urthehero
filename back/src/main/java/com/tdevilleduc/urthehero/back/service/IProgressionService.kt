package com.tdevilleduc.urthehero.back.service

import com.tdevilleduc.urthehero.back.model.Progression
import java.util.*

interface IProgressionService {
    fun doProgressionAction(personId: Int, storyId: Int, newPageId: Int): Progression
    fun findByPersonId(personId: Int): MutableList<Progression>
    fun findByPersonIdAndStoryId(personId: Int, storyId: Int): Optional<Progression>
    fun countPersonsByStoryId(storyId: Int): Long
}