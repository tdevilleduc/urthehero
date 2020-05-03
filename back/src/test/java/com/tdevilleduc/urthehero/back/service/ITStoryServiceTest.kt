package com.tdevilleduc.urthehero.back.service

import com.tdevilleduc.urthehero.back.AbstractITTest
import com.tdevilleduc.urthehero.back.BackApplication
import com.tdevilleduc.urthehero.back.dao.StoryDao
import com.tdevilleduc.urthehero.back.model.Story
import com.tdevilleduc.urthehero.back.util.TestUtil
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.*


@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [BackApplication::class])
internal class ITStoryServiceTest : AbstractITTest() {

    @Autowired
    private lateinit var storyService: IStoryService

    @Autowired
    private lateinit var storyDao: StoryDao


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