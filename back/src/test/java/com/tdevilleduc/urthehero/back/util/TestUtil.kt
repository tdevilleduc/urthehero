package com.tdevilleduc.urthehero.back.util

import com.tdevilleduc.urthehero.back.model.*
import org.testcontainers.shaded.org.apache.commons.lang.RandomStringUtils
import java.util.*

object TestUtil {
    private val random: Random = Random()
    fun createStory(): Story {
        val storyAuthorId = random.nextInt()
        val storyFirstPageId = random.nextInt()
        val title = RandomStringUtils.random(20)
        val detailedText = RandomStringUtils.random(20)
        val image = RandomStringUtils.random(20)
        return Story(0, storyAuthorId, storyFirstPageId, title, detailedText, image)
    }

    fun createStoryDto(authorId: Int, firstPageId: Int): StoryDTO {
        val id = null
        val title = RandomStringUtils.random(20)
        val detailedText = RandomStringUtils.random(20)
        val image = RandomStringUtils.random(20)
        return StoryDTO(id, title, authorId, firstPageId, detailedText, image)
    }

    fun createUserDto(): UserDTO {
        val id = null
        val username = RandomStringUtils.random(20)
        val password = RandomStringUtils.random(20)
        return UserDTO(id, username, password)
    }

    fun createUser(): User {
        val id = null
        val username = RandomStringUtils.random(20)
        val password = RandomStringUtils.random(20)
        return User(id, username, password)
    }

    fun createPageDto(): PageDTO {
        val id = null
        val text = RandomStringUtils.random(20)
        val image = RandomStringUtils.random(20)
        return PageDTO(id, text, image)
    }

    fun createPage(): Page {
        val id = 0
        val text = RandomStringUtils.random(20)
        val image = RandomStringUtils.random(20)
        return Page(id, text, image)
    }

    fun createEnemyDto(): EnemyDTO {
        val id = null
        val name = RandomStringUtils.random(20)
        val image = RandomStringUtils.random(20)
        val level = random.nextInt()
        val lifePoints = random.nextInt()
        return EnemyDTO(id, name, image, level, lifePoints)
    }

    fun createEnemy(): Enemy {
        val id = 0
        val name = RandomStringUtils.random(20)
        val image = RandomStringUtils.random(20)
        val level = random.nextInt()
        val lifePoints = random.nextInt()
        return Enemy(id, name, image, level, lifePoints)
    }
}