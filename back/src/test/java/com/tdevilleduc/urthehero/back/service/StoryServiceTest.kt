package com.tdevilleduc.urthehero.back.service

import com.tdevilleduc.urthehero.back.AbstractTest
import com.tdevilleduc.urthehero.back.service.impl.StoryService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest
internal class StoryServiceTest : AbstractTest() {
    @Autowired
    private lateinit var storyService: StoryService

    @Test
    fun exists_thenCorrect() {
        val storyId = 1
        val exists = storyService.exists(storyId)
        Assertions.assertTrue(exists)
    }

    @Test
    fun notExists_thenCorrect() {
        val storyId = 41
        val notExists = storyService.notExists(storyId)
        Assertions.assertTrue(notExists)
    }

    @Test
    fun findByPageId_thenCorrect() {
        val storyId = 1
        val story = storyService.findById(storyId)
        Assertions.assertEquals(Integer.valueOf(1), story.storyId)
        Assertions.assertEquals("Ulysse", story.title)
        Assertions.assertEquals(Integer.valueOf(1), story.authorId)
        Assertions.assertEquals(Integer.valueOf(1), story.firstPageId)
        Assertions.assertEquals(java.lang.Long.valueOf(3), story.numberOfReaders)
    }
}