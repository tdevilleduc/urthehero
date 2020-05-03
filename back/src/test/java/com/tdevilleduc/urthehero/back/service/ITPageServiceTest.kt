package com.tdevilleduc.urthehero.back.service

import com.tdevilleduc.urthehero.back.AbstractITTest
import com.tdevilleduc.urthehero.back.BackApplication
import com.tdevilleduc.urthehero.back.dao.PageDao
import com.tdevilleduc.urthehero.back.model.Page
import com.tdevilleduc.urthehero.back.util.TestUtil
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.*


@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [BackApplication::class])
internal class ITPageServiceTest : AbstractITTest() {

    @Autowired
    private lateinit var pageService: IPageService

    @Autowired
    private lateinit var pageDao: PageDao


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