package com.tdevilleduc.urthehero.back.service

import com.tdevilleduc.urthehero.back.AbstractTest
import com.tdevilleduc.urthehero.back.dao.PageDao
import com.tdevilleduc.urthehero.back.dao.StoryDao
import com.tdevilleduc.urthehero.back.model.Page
import com.tdevilleduc.urthehero.back.model.Story
import com.tdevilleduc.urthehero.back.service.impl.StoryService
import com.tdevilleduc.urthehero.back.util.TestUtil
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.*

@ExtendWith(SpringExtension::class)
@SpringBootTest
internal class StoryServiceTest : AbstractTest() {
    @Autowired
    private lateinit var storyService: StoryService
    @Autowired
    private lateinit var storyDao: StoryDao

    @Test
    fun test_exists_thenCorrect() {
        val storyId = 1
        val exists = storyService.exists(storyId)
        Assertions.assertTrue(exists)
    }

    @Test
    fun test_exists_withIdNull() {
        val storyId = null
        val exists = storyService.exists(storyId)
        Assertions.assertFalse(exists)
    }

    @Test
    fun test_notExists_thenCorrect() {
        val storyId = 41
        val notExists = storyService.notExists(storyId)
        Assertions.assertTrue(notExists)
    }

    @Test
    fun test_findByPageId_thenCorrect() {
        val storyId = 1
        val story = storyService.findById(storyId)
        Assertions.assertEquals(1, story.storyId)
        Assertions.assertEquals("Ulysse", story.title)
        Assertions.assertEquals(1, story.authorId)
        Assertions.assertEquals(1, story.firstPageId)
        Assertions.assertEquals(3, story.numberOfReaders)
        Assertions.assertEquals(4, story.numberOfPages)
    }

    @Test
    fun test_delete_thenSuccess() {
        var story = TestUtil.createStory()
        story = storyDao.save(story)
        Assertions.assertNotNull(story.storyId)
        val optionalBefore: Optional<Story> = storyDao.findById(story.storyId)
        Assertions.assertTrue(optionalBefore.isPresent)

        // delete story entity
        storyService.delete(story.storyId)

        val optionalAfter: Optional<Story> = storyDao.findById(story.storyId)
        Assertions.assertTrue(optionalAfter.isEmpty)
    }
}