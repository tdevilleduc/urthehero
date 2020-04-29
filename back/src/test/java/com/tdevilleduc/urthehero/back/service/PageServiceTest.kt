package com.tdevilleduc.urthehero.back.service

import com.tdevilleduc.urthehero.back.AbstractTest
import com.tdevilleduc.urthehero.back.BackApplication
import com.tdevilleduc.urthehero.back.dao.PageDao
import com.tdevilleduc.urthehero.back.exceptions.PageNotFoundException
import com.tdevilleduc.urthehero.back.model.Page
import com.tdevilleduc.urthehero.back.model.Position
import com.tdevilleduc.urthehero.back.model.User
import com.tdevilleduc.urthehero.back.service.impl.PageService
import com.tdevilleduc.urthehero.back.util.TestUtil
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.function.Executable
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.*

@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [BackApplication::class])
internal class PageServiceTest : AbstractTest() {
    @Autowired
    private lateinit var pageService: PageService
    @Autowired
    private lateinit var pageDao: PageDao


    @Test
    fun test_exists_withIdNull() {
        val pageId = null
        val exists = pageService.exists(pageId)
        Assertions.assertFalse(exists)
    }

    @Test
    fun test_findById_thenCorrect() {
        val pageId = 1
        val page = pageService.findById(pageId)
        Assertions.assertNotNull(page)
        Assertions.assertEquals(pageId, page.id)
        Assertions.assertEquals("image3", page.image)
        Assertions.assertEquals("Ulysse", page.text)
        val nextPageList = page.nextPageList
        Assertions.assertNotNull(nextPageList)
        Assertions.assertFalse(nextPageList.isEmpty())
        Assertions.assertEquals(3, nextPageList.size)
        val nextPage1 = nextPageList[0]
        Assertions.assertNotNull(nextPage1)
        Assertions.assertEquals(1, nextPage1.id)
        Assertions.assertEquals(2, nextPage1.destinationPageId)
        Assertions.assertEquals(1, nextPage1.pageId)
        Assertions.assertEquals("gauche", nextPage1.text)
        Assertions.assertEquals(Position.LEFT, nextPage1.position)
        val nextPage2 = nextPageList[1]
        Assertions.assertNotNull(nextPage2)
        Assertions.assertEquals(2, nextPage2.id)
        Assertions.assertEquals(3, nextPage2.destinationPageId)
        Assertions.assertEquals(1, nextPage2.pageId)
        Assertions.assertEquals("droite", nextPage2.text)
        Assertions.assertEquals(Position.RIGHT, nextPage2.position)
        val nextPage3 = nextPageList[2]
        Assertions.assertNotNull(nextPage3)
        Assertions.assertEquals(3, nextPage3.id)
        Assertions.assertEquals(8, nextPage3.destinationPageId)
        Assertions.assertEquals(1, nextPage3.pageId)
        Assertions.assertEquals("centre", nextPage3.text)
        Assertions.assertEquals(Position.CENTER, nextPage3.position)
    }

    @Test
    fun test_findById_thenNotFound() {
        val pageId = 13
        Assertions.assertThrows(PageNotFoundException::class.java, Executable { pageService.findById(pageId) } )
    }

    @Test
    fun delete_thenNotFound() {
        val pageId = 13
        Assertions.assertThrows(PageNotFoundException::class.java, Executable { pageService.delete(pageId) })
    }

    @Test
    fun test_countByStoryId_thenCorrect() {
        val storyId = 1
        val numberOfPages = pageService.countByStoryId(storyId)
        Assertions.assertNotNull(numberOfPages)
        Assertions.assertEquals(4, numberOfPages)
    }

    @Test
    fun test_delete_thenSuccess() {
        var page = TestUtil.createPage()
        page = pageDao.save(page)
        Assertions.assertNotNull(page.id)
        val optionalBefore: Optional<Page> = pageDao.findById(page.id)
        Assertions.assertTrue(optionalBefore.isPresent)

        // delete page entity
        pageService.delete(page.id)

        val optionalAfter: Optional<Page> = pageDao.findById(page.id)
        Assertions.assertTrue(optionalAfter.isEmpty)
    }
}