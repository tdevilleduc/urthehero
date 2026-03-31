package com.tdevilleduc.urthehero.back.service

import com.tdevilleduc.urthehero.back.dao.PageDao
import com.tdevilleduc.urthehero.back.exceptions.PageNotFoundException
import com.tdevilleduc.urthehero.back.service.impl.NextPageService
import com.tdevilleduc.urthehero.back.service.impl.PageService
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
internal class PageServiceTest {
    private val random: Random = Random()

    @InjectMocks
    private lateinit var pageService: PageService
    @Mock
    private lateinit var pageDao: PageDao
    @Mock
    private lateinit var nextPageService: NextPageService
    @Mock
    private lateinit var storyService: StoryService

    @Test
    fun test_exists_withIdNull() {
        val pageId = null
        val exists = pageService.exists(pageId)
        Assertions.assertFalse(exists)
    }

    @Test
    fun test_notExists_thenSuccess() {
        val pageId = random.nextInt()
        Mockito.`when`(pageDao.findById(pageId)).thenReturn(Optional.empty())

        val notExists = pageService.notExists(pageId)
        Assertions.assertTrue(notExists)
    }

    @Test
    fun test_findById_thenCorrect() {
        val expectedPage = TestUtil.createPage()
        Mockito.`when`(pageDao.findById(expectedPage.id)).thenReturn(Optional.of(expectedPage))

        val page = pageService.findById(expectedPage.id)
        Assertions.assertNotNull(page)
        Assertions.assertEquals(expectedPage.id, page.id)
        Assertions.assertEquals(expectedPage.image, page.image)
        Assertions.assertEquals(expectedPage.text, page.text)
    }

    @Test
    fun test_findById_thenNotFound() {
        val pageId = random.nextInt()
        Mockito.`when`(pageDao.findById(pageId)).thenReturn(Optional.empty())

        Assertions.assertThrows(PageNotFoundException::class.java) { pageService.findById(pageId) }
    }

    @Test
    fun delete_thenNotFound() {
        val pageId = random.nextInt()
        Mockito.`when`(pageDao.findById(pageId)).thenReturn(Optional.empty())

        Assertions.assertThrows(PageNotFoundException::class.java) { pageService.delete(pageId) }
    }

    @Test
    fun test_countByStoryId_thenCorrect() {
        val storyId = random.nextInt()
        val expectedNumberOfPages = random.nextLong()
        Mockito.`when`(pageDao.countByStoryId(storyId)).thenReturn(expectedNumberOfPages)

        val numberOfPages = pageService.countByStoryId(storyId)
        Assertions.assertNotNull(numberOfPages)
        Assertions.assertEquals(expectedNumberOfPages, numberOfPages)
    }
}