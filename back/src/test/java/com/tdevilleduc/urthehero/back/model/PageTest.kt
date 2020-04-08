package com.tdevilleduc.urthehero.back.model

import com.tdevilleduc.urthehero.back.utils.TestUtils
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class PageTest {
    @Test
    fun test_Constructor() {
        val pageId = 46
        val pageText = "test de titre"
        val pageImage = "image de ouf"
        val page = Page(pageText, pageImage)
        page.id = pageId
        Assertions.assertEquals(page.id!!, pageId)
        Assertions.assertEquals(page.text, pageText)
        Assertions.assertEquals(page.image, pageImage)
        Assertions.assertTrue(page.nextPageList.isEmpty())
    }
}