package com.tdevilleduc.urthehero.back.model

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class StoryTest {
    @Test
    fun test_Constructor() {
        val storyId = 42
        val storyTitle = "test de titre"
        val storyAuthorId = 23
        val storyFirstPageId = 98
        val storyDetailedText = "Tintin long tintin"
        val storyImage = "/chemin/to/image"
        val story = Story(storyId, storyAuthorId, storyFirstPageId, storyTitle, storyDetailedText, storyImage)
        Assertions.assertEquals(story.storyId!!, storyId)
        Assertions.assertEquals(story.authorId!!, storyAuthorId)
        Assertions.assertEquals(story.firstPageId!!, storyFirstPageId)
        Assertions.assertEquals(story.title, storyTitle)
        Assertions.assertEquals(story.detailedText, storyDetailedText)
        Assertions.assertEquals(story.image, storyImage)
        Assertions.assertNull(story.currentPageId)
        val secondStory = Story()
        secondStory.storyId = storyId
        secondStory.authorId = storyAuthorId
        secondStory.firstPageId = storyFirstPageId
        secondStory.title = storyTitle
        secondStory.detailedText = storyDetailedText
        secondStory.image = storyImage
        Assertions.assertEquals(secondStory.toString(), story.toString())
        Assertions.assertEquals(secondStory, story)
    }
}