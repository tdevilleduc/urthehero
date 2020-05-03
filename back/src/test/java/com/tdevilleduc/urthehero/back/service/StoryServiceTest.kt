package com.tdevilleduc.urthehero.back.service

import com.tdevilleduc.urthehero.back.dao.StoryDao
import com.tdevilleduc.urthehero.back.exceptions.StoryNotFoundException
import com.tdevilleduc.urthehero.back.service.impl.PageService
import com.tdevilleduc.urthehero.back.service.impl.ProgressionService
import com.tdevilleduc.urthehero.back.service.impl.StoryService
import com.tdevilleduc.urthehero.back.util.TestUtil
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*

@ExtendWith(MockitoExtension::class)
internal class StoryServiceTest {
    private val random: Random = Random()

    @InjectMocks
    private lateinit var storyService: StoryService
    @Mock
    private lateinit var storyDao: StoryDao
    @Mock
    private lateinit var progressionService: ProgressionService
    @Mock
    private lateinit var pageService: PageService

    @Test
    fun test_exists_withIdNull() {
        val storyId = null
        val exists = storyService.exists(storyId)
        Assertions.assertFalse(exists)
    }

    @Test
    fun test_notExists_thenSuccess() {
        val storyId = random.nextInt()
        Mockito.`when`(storyDao.findById(storyId)).thenReturn(Optional.empty())

        val notExists = storyService.notExists(storyId)
        Assertions.assertTrue(notExists)
    }

    @Test
    fun test_findByPageId_thenCorrect() {
        val expectedPage = TestUtil.createStory()
        Mockito.`when`(storyDao.findById(expectedPage.storyId)).thenReturn(Optional.of(expectedPage))

        val story = storyService.findById(expectedPage.storyId)
        Assertions.assertNotNull(story)
        Assertions.assertEquals(expectedPage.storyId, story.storyId)
        Assertions.assertEquals(expectedPage.title, story.title)
        Assertions.assertEquals(expectedPage.authorId, story.authorId)
        Assertions.assertEquals(expectedPage.firstPageId, story.firstPageId)
        Assertions.assertEquals(expectedPage.numberOfReaders, story.numberOfReaders)
        Assertions.assertEquals(expectedPage.numberOfPages, story.numberOfPages)
    }

    @Test
    fun test_findById_thenNotFound() {
        val storyId = random.nextInt()
        Mockito.`when`(storyDao.findById(storyId)).thenReturn(Optional.empty())

        Assertions.assertThrows(StoryNotFoundException::class.java) { storyService.findById(storyId) }
    }
}