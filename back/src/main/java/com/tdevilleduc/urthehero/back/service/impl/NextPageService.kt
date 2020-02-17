package com.tdevilleduc.urthehero.back.service.impl

import com.tdevilleduc.urthehero.back.constant.ApplicationConstants
import com.tdevilleduc.urthehero.back.dao.NextPageDao
import com.tdevilleduc.urthehero.back.exceptions.PageNotFoundException
import com.tdevilleduc.urthehero.back.model.NextPage
import com.tdevilleduc.urthehero.back.service.INextPageService
import com.tdevilleduc.urthehero.back.service.IPageService
import org.slf4j.helpers.MessageFormatter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class NextPageService : INextPageService {
    @Autowired
    private lateinit var pageService: IPageService
    @Autowired
    private lateinit var nextPageDao: NextPageDao

    override fun findByPageId(pageId: Int): MutableList<NextPage> {
        if (pageService.notExists(pageId)) {
            throw PageNotFoundException(MessageFormatter.format(ApplicationConstants.ERROR_MESSAGE_PAGE_DOESNOT_EXIST, pageId).message)
        }
        return nextPageDao.findByPageId(pageId)
    }
}