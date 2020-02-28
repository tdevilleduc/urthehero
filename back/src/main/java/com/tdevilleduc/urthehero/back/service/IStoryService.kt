package com.tdevilleduc.urthehero.back.service

import com.tdevilleduc.urthehero.back.model.Story
import com.tdevilleduc.urthehero.back.model.StoryDTO

interface IStoryService {
    fun exists(storyId: Int): Boolean
    fun notExists(storyId: Int): Boolean
    fun findById(storyId: Int): Story
    fun findAll(): MutableList<Story>
    fun findByPersonId(personId: Int): MutableList<Story>
    fun createOrUpdate(storyDto: StoryDTO): StoryDTO
    fun delete(storyId: Int)
}