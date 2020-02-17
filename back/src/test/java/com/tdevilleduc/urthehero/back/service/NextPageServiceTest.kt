package com.tdevilleduc.urthehero.back.service

import com.tdevilleduc.urthehero.back.AbstractTest
import com.tdevilleduc.urthehero.back.BackApplication
import com.tdevilleduc.urthehero.back.exceptions.PageNotFoundException
import com.tdevilleduc.urthehero.back.model.Position
import com.tdevilleduc.urthehero.back.service.impl.NextPageService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.function.Executable
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [BackApplication::class])
internal class NextPageServiceTest : AbstractTest() {
    @Autowired
    private lateinit var nextPageService: NextPageService

    @Test
    fun test_findByPageId_thenCorrect() {
        val pageId = 1
        val nextPageList = nextPageService.findByPageId(pageId)
        Assertions.assertNotNull(nextPageList)
        Assertions.assertFalse(nextPageList.isEmpty())
        Assertions.assertEquals(3, nextPageList.size)
        val nextPage1 = nextPageList[0]
        Assertions.assertNotNull(nextPage1)
        Assertions.assertEquals(Integer.valueOf(1), nextPage1.id)
        Assertions.assertEquals(Integer.valueOf(2), nextPage1.destinationPageId)
        Assertions.assertEquals(Integer.valueOf(1), nextPage1.pageId)
        Assertions.assertEquals("gauche", nextPage1.text)
        Assertions.assertEquals(Position.LEFT, nextPage1.position)
        val nextPage2 = nextPageList[1]
        Assertions.assertNotNull(nextPage2)
        Assertions.assertEquals(Integer.valueOf(2), nextPage2.id)
        Assertions.assertEquals(Integer.valueOf(3), nextPage2.destinationPageId)
        Assertions.assertEquals(Integer.valueOf(1), nextPage2.pageId)
        Assertions.assertEquals("droite", nextPage2.text)
        Assertions.assertEquals(Position.RIGHT, nextPage2.position)
        val nextPage3 = nextPageList[2]
        Assertions.assertNotNull(nextPage3)
        Assertions.assertEquals(Integer.valueOf(3), nextPage3.id)
        Assertions.assertEquals(Integer.valueOf(8), nextPage3.destinationPageId)
        Assertions.assertEquals(Integer.valueOf(1), nextPage3.pageId)
        Assertions.assertEquals("centre", nextPage3.text)
        Assertions.assertEquals(Position.CENTER, nextPage3.position)
    }

    @Test
    fun test_findByPageId_thenNotFound() {
        val pageId = 13
        Assertions.assertThrows(PageNotFoundException::class.java, Executable { nextPageService.findByPageId(pageId) })
    }
}