package com.tdevilleduc.urthehero.back.service.impl

import com.tdevilleduc.urthehero.back.config.Mapper
import com.tdevilleduc.urthehero.back.constant.ResilienceConstants
import com.tdevilleduc.urthehero.back.constant.ApplicationConstants
import com.tdevilleduc.urthehero.back.dao.StoryDao
import com.tdevilleduc.urthehero.back.exceptions.StoryNotFoundException
import com.tdevilleduc.urthehero.back.model.Person
import com.tdevilleduc.urthehero.back.model.Story
import com.tdevilleduc.urthehero.back.model.StoryDTO
import com.tdevilleduc.urthehero.back.service.IProgressionService
import com.tdevilleduc.urthehero.back.service.IStoryService
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.slf4j.helpers.MessageFormatter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.util.Assert
import java.util.stream.Collectors

@Service
class StoryService : IStoryService {
    val logger: Logger = LoggerFactory.getLogger(StoryService::class.java)

    @Autowired
    private lateinit var progressionService: IProgressionService
    @Autowired
    private lateinit var storyDao: StoryDao

    override fun exists(storyId: Int): Boolean {
        Assert.notNull(storyId, "The story parameter is mandatory !")
        val optional = storyDao.findById(storyId)
        if (optional.isEmpty) {
                logger.error(ApplicationConstants.ERROR_MESSAGE_STORY_DOESNOT_EXIST, storyId)
                return false
            }
        return true
    }

    override fun notExists(storyId: Int): Boolean {
        return !exists(storyId)
    }

    override fun findById(storyId: Int): Story {
        Assert.notNull(storyId, ApplicationConstants.CHECK_STORYID_PARAMETER_MANDATORY!!)
        val optional = storyDao.findById(storyId)
        if (optional.isPresent) {
            return fillStoryWithNumberOfReaders(optional.get())
        } else {
            throw StoryNotFoundException(MessageFormatter.format(ApplicationConstants.ERROR_MESSAGE_STORY_DOESNOT_EXIST, storyId).message)
        }
    }

    @CircuitBreaker(name = ResilienceConstants.INSTANCE_STORY_SERVICE, fallbackMethod = "emptyList")
    override fun findAll(): MutableList<Story> {
        return storyDao.findAll().stream()
                .map { story: Story -> fillStoryWithNumberOfReaders(story) }
                .collect(Collectors.toList())
    }

    //NOSONAR - This method is a ChaosMonkey CircuitBreaker fallback method
    private fun emptyList(e: Throwable): MutableList<Person> {
        logger.error("Unable to retrieve list", e)
        return emptyList<Person>().toMutableList()
    }

    private fun fillStoryWithNumberOfReaders(story: Story): Story {
        val numberOfReaders = progressionService.countByStoryId(story.storyId!!)
        story.numberOfReaders = numberOfReaders
        return story
    }

    override fun createOrUpdate(storyDto: StoryDTO): StoryDTO {
        val story: Story = Mapper.convert(storyDto)
        return Mapper.convert(storyDao.save(story))
    }

    override fun delete(storyId: Int) {
        Assert.notNull(storyId, ApplicationConstants.CHECK_STORYID_PARAMETER_MANDATORY!!)
        val story = findById(storyId)
        storyDao.delete(story)
    }
}