package com.tdevilleduc.urthehero.back.utils

import com.tdevilleduc.urthehero.back.model.Story
import com.tdevilleduc.urthehero.back.model.StoryDTO
import org.testcontainers.shaded.org.apache.commons.lang.RandomStringUtils
import java.util.*

object TestUtils {
    private val random: Random = Random()
    fun createRandomStory(): Story {
        val storyId = random.nextInt()
        val storyTitle = RandomStringUtils.random(20)
        val storyAuthorId = random.nextInt()
        val storyFirstPageId = random.nextInt()
        val storyDetailedText = RandomStringUtils.random(20)
        val storyImage = RandomStringUtils.random(20)
        return Story(storyId, storyAuthorId, storyFirstPageId, storyTitle, storyDetailedText, storyImage)
    }

    fun createStory(authorId: Int, firstPageId: Int): StoryDTO {
        val storyId = random.nextInt()
        val storyTitle = RandomStringUtils.random(20)
        val storyDetailedText = RandomStringUtils.random(20)
        val storyImage = RandomStringUtils.random(20)
        return StoryDTO(storyId, storyTitle, authorId, firstPageId, storyDetailedText, storyImage)
    }
}