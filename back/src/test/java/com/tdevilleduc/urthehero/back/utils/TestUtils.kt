package com.tdevilleduc.urthehero.back.utils

import com.tdevilleduc.urthehero.back.model.PageDTO
import com.tdevilleduc.urthehero.back.model.PersonDTO
import com.tdevilleduc.urthehero.back.model.Story
import com.tdevilleduc.urthehero.back.model.StoryDTO
import org.testcontainers.shaded.org.apache.commons.lang.RandomStringUtils
import java.util.*

object TestUtils {
    private val random: Random = Random()
    fun createRandomStory(): Story {
        val id = random.nextInt()
        val storyAuthorId = random.nextInt()
        val storyFirstPageId = random.nextInt()
        val title = RandomStringUtils.random(20)
        val detailedText = RandomStringUtils.random(20)
        val image = RandomStringUtils.random(20)
        return Story(id, storyAuthorId, storyFirstPageId, title, detailedText, image)
    }

    fun createStory(authorId: Int, firstPageId: Int): StoryDTO {
        val id = random.nextInt()
        val title = RandomStringUtils.random(20)
        val detailedText = RandomStringUtils.random(20)
        val image = RandomStringUtils.random(20)
        return StoryDTO(id, title, authorId, firstPageId, detailedText, image)
    }

    fun createPerson(): PersonDTO {
        val id = random.nextInt()
        val login = RandomStringUtils.random(20)
        val displayName = RandomStringUtils.random(20)
        val email = RandomStringUtils.random(20)
        val password = RandomStringUtils.random(20)
        return PersonDTO(id, login, displayName, email, password)
    }

    fun createPage(): PageDTO {
        val id = random.nextInt()
        val text = RandomStringUtils.random(20)
        val image = RandomStringUtils.random(20)
        return PageDTO(id, text, image)
    }
}