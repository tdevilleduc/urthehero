package com.tdevilleduc.urthehero.back.service

import com.tdevilleduc.urthehero.back.dao.PageDao
import com.tdevilleduc.urthehero.back.exceptions.PageNotFoundException
import com.tdevilleduc.urthehero.back.service.impl.NextPageService
import com.tdevilleduc.urthehero.back.service.impl.PageService
import com.tdevilleduc.urthehero.back.service.impl.StoryService
import com.tdevilleduc.urthehero.back.util.TestUtil
import org.junit.Before
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
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

    @Before
    fun initMocks() {
        MockitoAnnotations.initMocks(this)
    }


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
//        val nextPageList = page.nextPageList
//        Assertions.assertNotNull(nextPageList)
//        Assertions.assertFalse(nextPageList.isEmpty())
//        Assertions.assertEquals(3, nextPageList.size)
//        val nextPage1 = nextPageList[0]
//        Assertions.assertNotNull(nextPage1)
//        Assertions.assertEquals(1, nextPage1.id)
//        Assertions.assertEquals(2, nextPage1.destinationPageId)
//        Assertions.assertEquals(1, nextPage1.pageId)
//        Assertions.assertEquals("gauche", nextPage1.text)
//        Assertions.assertEquals(Position.LEFT, nextPage1.position)
//        val nextPage2 = nextPageList[1]
//        Assertions.assertNotNull(nextPage2)
//        Assertions.assertEquals(2, nextPage2.id)
//        Assertions.assertEquals(3, nextPage2.destinationPageId)
//        Assertions.assertEquals(1, nextPage2.pageId)
//        Assertions.assertEquals("droite", nextPage2.text)
//        Assertions.assertEquals(Position.RIGHT, nextPage2.position)
//        val nextPage3 = nextPageList[2]
//        Assertions.assertNotNull(nextPage3)
//        Assertions.assertEquals(3, nextPage3.id)
//        Assertions.assertEquals(8, nextPage3.destinationPageId)
//        Assertions.assertEquals(1, nextPage3.pageId)
//        Assertions.assertEquals("centre", nextPage3.text)
//        Assertions.assertEquals(Position.CENTER, nextPage3.position)
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