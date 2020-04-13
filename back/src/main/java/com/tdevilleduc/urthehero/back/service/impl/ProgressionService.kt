package com.tdevilleduc.urthehero.back.service.impl

import com.tdevilleduc.urthehero.back.constant.ApplicationConstants
import com.tdevilleduc.urthehero.back.dao.ProgressionDao
import com.tdevilleduc.urthehero.back.exceptions.PageNotFoundException
import com.tdevilleduc.urthehero.back.exceptions.ProgressionNotFoundException
import com.tdevilleduc.urthehero.back.exceptions.StoryNotFoundException
import com.tdevilleduc.urthehero.back.exceptions.UserNotFoundException
import com.tdevilleduc.urthehero.back.model.Progression
import com.tdevilleduc.urthehero.back.service.IPageService
import com.tdevilleduc.urthehero.back.service.IProgressionService
import com.tdevilleduc.urthehero.back.service.IStoryService
import com.tdevilleduc.urthehero.back.service.IUserService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.slf4j.helpers.MessageFormatter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.util.Assert
import java.util.*

@Service
class ProgressionService : IProgressionService {

    @Autowired
    private lateinit var storyService: IStoryService
    @Autowired
    private lateinit var userService: IUserService
    @Autowired
    private lateinit var pageService: IPageService
    @Autowired
    private lateinit var progressionDao: ProgressionDao

    override fun doProgressionAction(userId: Int, storyId: Int, newPageId: Int): Progression {
        Assert.notNull(userId, ApplicationConstants.CHECK_USERID_PARAMETER_MANDATORY)
        Assert.notNull(storyId, ApplicationConstants.CHECK_STORYID_PARAMETER_MANDATORY)
        Assert.notNull(newPageId, ApplicationConstants.CHECK_PAGEID_PARAMETER_MANDATORY)
        if (userService.notExists(userId)) {
            throw UserNotFoundException(MessageFormatter.format(ApplicationConstants.ERROR_MESSAGE_USER_DOESNOT_EXIST, userId).message)
        }
        if (storyService.notExists(storyId)) {
            throw StoryNotFoundException(MessageFormatter.format(ApplicationConstants.ERROR_MESSAGE_STORY_DOESNOT_EXIST, storyId).message)
        }
        if (pageService.notExists(newPageId)) {
            throw PageNotFoundException(MessageFormatter.format(ApplicationConstants.ERROR_MESSAGE_PAGE_DOESNOT_EXIST, newPageId).message)
        }
        val progression = this.findByUserIdAndStoryId(userId, storyId)
        // TODO: vérifier qu'on a le droit d'avancer sur cette page depuis la page précédente
        progression.actualPageId = newPageId
        return progressionDao.save(progression)
    }

    override fun findByUserId(userId: Int): MutableList<Progression> {
        Assert.notNull(userId, ApplicationConstants.CHECK_USERID_PARAMETER_MANDATORY)
        if (userService.notExists(userId)) {
            throw UserNotFoundException(MessageFormatter.format(ApplicationConstants.ERROR_MESSAGE_USER_DOESNOT_EXIST, userId).message)
        }
        return progressionDao.findByUserId(userId)
    }

    override fun findByUserIdAndStoryId(userId: Int, storyId: Int): Progression {
        val optional = progressionDao.findByUserIdAndStoryId(userId, storyId)
        return if (optional.isPresent) {
            optional.get()
        } else {
            throw ProgressionNotFoundException("Aucune progression avec le userId $userId et le storyId $storyId")
        }
    }

    override fun countUsersByStoryId(storyId: Int): Long {
        Assert.notNull(storyId, ApplicationConstants.CHECK_STORYID_PARAMETER_MANDATORY)
        if (storyService.notExists(storyId)) {
            throw StoryNotFoundException(MessageFormatter.format(ApplicationConstants.ERROR_MESSAGE_STORY_DOESNOT_EXIST, storyId).message)
        }
        return progressionDao.countByStoryId(storyId)
    }
}