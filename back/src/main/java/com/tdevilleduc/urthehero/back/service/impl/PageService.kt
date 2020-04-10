package com.tdevilleduc.urthehero.back.service.impl

import com.tdevilleduc.urthehero.back.config.Mapper
import com.tdevilleduc.urthehero.back.constant.ApplicationConstants
import com.tdevilleduc.urthehero.back.dao.PageDao
import com.tdevilleduc.urthehero.back.exceptions.PageNotFoundException
import com.tdevilleduc.urthehero.back.exceptions.StoryNotFoundException
import com.tdevilleduc.urthehero.back.model.Page
import com.tdevilleduc.urthehero.back.model.PageDTO
import com.tdevilleduc.urthehero.back.service.INextPageService
import com.tdevilleduc.urthehero.back.service.IPageService
import com.tdevilleduc.urthehero.back.service.IStoryService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.slf4j.helpers.MessageFormatter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.util.Assert

@Service
class PageService : IPageService {
    val logger: Logger = LoggerFactory.getLogger(PageService::class.java)

    @Autowired
    private lateinit var nextPageService: INextPageService
    @Autowired
    private lateinit var storyService: IStoryService
    @Autowired
    private lateinit var pageDao: PageDao

    override fun exists(pageId: Int): Boolean {
        Assert.notNull(pageId, ApplicationConstants.CHECK_PAGEID_PARAMETER_MANDATORY!!)
        val optional = pageDao.findById(pageId)
        if (optional.isEmpty) {
            logger.error(ApplicationConstants.ERROR_MESSAGE_PAGE_DOESNOT_EXIST, pageId)
            return false
        }
        return true
    }

    override fun notExists(pageId: Int): Boolean {
        return !exists(pageId)
    }

    override fun findById(pageId: Int): Page {
        Assert.notNull(pageId, ApplicationConstants.CHECK_PAGEID_PARAMETER_MANDATORY!!)
        val optional = pageDao.findById(pageId)
        if (optional.isPresent) {
            return fillPageWithNextPages(optional.get())
        } else {
            throw PageNotFoundException(MessageFormatter.format(ApplicationConstants.ERROR_MESSAGE_PAGE_DOESNOT_EXIST, pageId).message)
        }
    }

    private fun fillPageWithNextPages(page: Page): Page {
        val nextPageList = nextPageService.findByPageId(page.id!!)
        page.nextPageList = nextPageList
        return page
    }

    override fun createOrUpdate(pageDto: PageDTO): PageDTO {
        val page: Page = Mapper.convert(pageDto)
        return Mapper.convert(pageDao.save(page))
    }

    override fun delete(pageId: Int) {
        Assert.notNull(pageId, ApplicationConstants.CHECK_PAGEID_PARAMETER_MANDATORY!!)
        val page = findById(pageId)
        pageDao.delete(page)
    }

    override fun countByStoryId(storyId: Int): Long {
        Assert.notNull(storyId, ApplicationConstants.CHECK_STORYID_PARAMETER_MANDATORY!!)
        if (storyService.notExists(storyId)) {
            throw StoryNotFoundException(MessageFormatter.format(ApplicationConstants.ERROR_MESSAGE_STORY_DOESNOT_EXIST, storyId).message)
        }
        return pageDao.countByStoryId(storyId)
    }
}