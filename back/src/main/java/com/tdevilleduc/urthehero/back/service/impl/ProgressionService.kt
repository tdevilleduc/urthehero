package com.tdevilleduc.urthehero.back.service.impl

import com.tdevilleduc.urthehero.back.constant.ApplicationConstants
import com.tdevilleduc.urthehero.back.constant.ResilienceConstants
import com.tdevilleduc.urthehero.back.dao.ProgressionDao
import com.tdevilleduc.urthehero.back.exceptions.PageNotFoundException
import com.tdevilleduc.urthehero.back.exceptions.PersonNotFoundException
import com.tdevilleduc.urthehero.back.exceptions.ProgressionNotFoundException
import com.tdevilleduc.urthehero.back.exceptions.StoryNotFoundException
import com.tdevilleduc.urthehero.back.model.Person
import com.tdevilleduc.urthehero.back.model.Progression
import com.tdevilleduc.urthehero.back.service.IPageService
import com.tdevilleduc.urthehero.back.service.IPersonService
import com.tdevilleduc.urthehero.back.service.IProgressionService
import com.tdevilleduc.urthehero.back.service.IStoryService
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.slf4j.helpers.MessageFormatter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.util.Assert
import java.util.*

@Service
class ProgressionService : IProgressionService {
    val logger: Logger = LoggerFactory.getLogger(ProgressionService::class.java)

    @Autowired
    private lateinit var storyService: IStoryService
    @Autowired
    private lateinit var personService: IPersonService
    @Autowired
    private lateinit var pageService: IPageService
    @Autowired
    private lateinit var progressionDao: ProgressionDao

    override fun doProgressionAction(personId: Int, storyId: Int, newPageId: Int): Progression {
        Assert.notNull(personId, ApplicationConstants.CHECK_PERSONID_PARAMETER_MANDATORY!!)
        Assert.notNull(storyId, ApplicationConstants.CHECK_STORYID_PARAMETER_MANDATORY!!)
        Assert.notNull(newPageId, ApplicationConstants.CHECK_PAGEID_PARAMETER_MANDATORY!!)
        if (personService.notExists(personId)) {
            throw PersonNotFoundException(MessageFormatter.format(ApplicationConstants.ERROR_MESSAGE_PERSON_DOESNOT_EXIST, personId).message)
        }
        if (storyService.notExists(storyId)) {
            throw StoryNotFoundException(MessageFormatter.format(ApplicationConstants.ERROR_MESSAGE_STORY_DOESNOT_EXIST, storyId).message)
        }
        if (pageService.notExists(newPageId)) {
            throw PageNotFoundException(MessageFormatter.format(ApplicationConstants.ERROR_MESSAGE_PAGE_DOESNOT_EXIST, newPageId).message)
        }
        val optional = progressionDao.findByPersonIdAndStoryId(personId, storyId)
        return if (optional.isPresent) {
            val progression = optional.get()
            // TODO: vérifier qu'on a le droit d'avancer sur cette page depuis la page précédente
            progression.actualPageId = newPageId
            progressionDao.save(progression)
        } else {
            throw ProgressionNotFoundException("Aucune progression avec le personId $personId et le storyId $storyId")
        }
    }

    @CircuitBreaker(name = ResilienceConstants.INSTANCE_PROGRESSION_SERVICE, fallbackMethod = "emptyList")
    override fun findByPersonId(personId: Int): MutableList<Progression> {
        logger.info("ProgressionService findByPersonId person {}", personId)
        Assert.notNull(personId, ApplicationConstants.CHECK_PERSONID_PARAMETER_MANDATORY!!)
        if (personService.notExists(personId)) {
            logger.info("notExists person {}", personId)
            throw PersonNotFoundException(MessageFormatter.format(ApplicationConstants.ERROR_MESSAGE_PERSON_DOESNOT_EXIST, personId).message)
        }
        logger.info("exists person {}", personId)
        return progressionDao.findByPersonId(personId)
    }

    //NOSONAR - This method is a ChaosMonkey CircuitBreaker fallback method
    private fun emptyList(e: Throwable): MutableList<Person> {
        logger.error("Unable to retrieve list", e)
        return emptyList<Person>().toMutableList()
    }

    override fun findByPersonIdAndStoryId(personId: Int, storyId: Int): Optional<Progression> {
        Assert.notNull(personId, ApplicationConstants.CHECK_PERSONID_PARAMETER_MANDATORY!!)
        Assert.notNull(storyId, ApplicationConstants.CHECK_STORYID_PARAMETER_MANDATORY!!)
        if (personService.notExists(personId)) {
            throw PersonNotFoundException(MessageFormatter.format(ApplicationConstants.ERROR_MESSAGE_PERSON_DOESNOT_EXIST, personId).message)
        }
        if (storyService.notExists(storyId)) {
            throw StoryNotFoundException(MessageFormatter.format(ApplicationConstants.ERROR_MESSAGE_STORY_DOESNOT_EXIST, storyId).message)
        }
        return progressionDao.findByPersonIdAndStoryId(personId, storyId)
    }

    override fun countPersonsByStoryId(storyId: Int): Long {
        Assert.notNull(storyId, ApplicationConstants.CHECK_STORYID_PARAMETER_MANDATORY!!)
        if (storyService.notExists(storyId)) {
            throw StoryNotFoundException(MessageFormatter.format(ApplicationConstants.ERROR_MESSAGE_STORY_DOESNOT_EXIST, storyId).message)
        }
        return progressionDao.countByStoryId(storyId)
    }
}