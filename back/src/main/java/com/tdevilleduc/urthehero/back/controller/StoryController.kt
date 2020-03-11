package com.tdevilleduc.urthehero.back.controller

import com.tdevilleduc.urthehero.back.constant.ApplicationConstants
import com.tdevilleduc.urthehero.back.exceptions.StoryInternalErrorException
import com.tdevilleduc.urthehero.back.model.Story
import com.tdevilleduc.urthehero.back.model.StoryDTO
import com.tdevilleduc.urthehero.back.service.IPageService
import com.tdevilleduc.urthehero.back.service.IPersonService
import com.tdevilleduc.urthehero.back.service.IStoryService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.slf4j.helpers.MessageFormatter
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.util.Assert
import org.springframework.web.bind.annotation.*
import java.util.concurrent.Callable
import javax.servlet.http.HttpServletRequest

@Tag(name = "Story", description = "Story Controller")
@RestController
@RequestMapping("/api/story")
internal class StoryController(private val storyService: IStoryService, private val personService: IPersonService, private val pageService: IPageService) {
    val logger: Logger = LoggerFactory.getLogger(StoryController::class.java)

    @GetMapping(value = ["/all"], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "\${swagger.controller.story.get-all.value}", description = "\${swagger.controller.story.get-all.notes}")
    @ResponseBody
    fun getAllStories(request: HttpServletRequest): Callable<ResponseEntity<MutableList<Story>>> = Callable {
        if (logger.isInfoEnabled) {
            logger.info(ApplicationConstants.CONTROLLER_CALL_LOG, request.requestURI)
        }
        ResponseEntity.ok(storyService.findAll())
    }

    @GetMapping(value = ["/{storyId}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "\${swagger.controller.story.get-by-id.value}", description = "\${swagger.controller.story.get-by-id.notes}")
    @ResponseBody
    fun getStoryById(request: HttpServletRequest,
                     @PathVariable storyId: Int): Callable<ResponseEntity<Story>> = Callable {
        if (logger.isInfoEnabled) {
            logger.info(ApplicationConstants.CONTROLLER_CALL_LOG, request.requestURI)
        }
        ResponseEntity.ok(storyService.findById(storyId))
    }

    @GetMapping(value = ["/all/person/{personId}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "\${swagger.controller.story.get-by-person-id.value}", description = "\${swagger.controller.story.get-by-person-id.notes}")
    @ResponseBody
    fun getStoryByPersonId(request: HttpServletRequest,
                           @PathVariable personId: Int): Callable<ResponseEntity<MutableList<Story>>> = Callable {
        if (logger.isInfoEnabled) {
            logger.info(ApplicationConstants.CONTROLLER_CALL_LOG, request.requestURI)
        }
        if (personService.notExists(personId)) {
            ResponseEntity.notFound().build<MutableList<Story>>()
        }
        logger.info(ApplicationConstants.CONTROLLER_CALL_LOG, request.requestURI)
        ResponseEntity.ok(storyService.findByPersonId(personId))
    }

    @PutMapping
    @ResponseBody
    fun createStory(request: HttpServletRequest,
                    @RequestBody storyDTO: StoryDTO): Callable<ResponseEntity<StoryDTO>> = Callable {
        if (logger.isInfoEnabled) {
            logger.info(ApplicationConstants.CONTROLLER_CALL_LOG, request.requestURI)
        }
        //TODO: déplacer les controles dans le service ?
        Assert.notNull(storyDTO.authorId) { throw StoryInternalErrorException("L'auteur de l'histoire passée en paramètre ne peut pas être null") }
        Assert.notNull(storyDTO.firstPageId) { throw StoryInternalErrorException("La première page de l'histoire passée en paramètre ne peut pas être null") }
        if (personService.notExists(storyDTO.authorId!!)) {
            throw StoryInternalErrorException(MessageFormatter.format(ApplicationConstants.ERROR_MESSAGE_PERSON_DOESNOT_EXIST, storyDTO.authorId).message)
        }
        if (pageService.notExists(storyDTO.firstPageId!!)) {
            throw StoryInternalErrorException(MessageFormatter.format(ApplicationConstants.ERROR_MESSAGE_PAGE_DOESNOT_EXIST, storyDTO.firstPageId).message)
        }
        if (storyService.exists(storyDTO.storyId!!)) {
            throw StoryInternalErrorException(MessageFormatter.format("L'id {} existe déjà. Elle ne peut être créée", storyDTO.storyId).message)
        }
        ResponseEntity.ok(storyService.createOrUpdate(storyDTO))
    }

    @PostMapping
    @ResponseBody
    fun updateStory(request: HttpServletRequest,
                    @RequestBody storyDto: StoryDTO): Callable<ResponseEntity<StoryDTO>> = Callable {
        if (logger.isInfoEnabled) {
            logger.info(ApplicationConstants.CONTROLLER_CALL_LOG, request.requestURI)
        }
        //TODO: déplacer les controles dans le service ?
        Assert.notNull(storyDto.authorId) { throw StoryInternalErrorException("L'auteur de l'histoire passée en paramètre ne peut pas être null") }
        Assert.notNull(storyDto.firstPageId) { throw StoryInternalErrorException("La première page de l'histoire passée en paramètre ne peut pas être null") }
        Assert.notNull(storyDto.storyId) { throw StoryInternalErrorException("L'identifiant de l'histoire passée en paramètre ne peut pas être null") }
        if (personService.notExists(storyDto.authorId!!)) {
            throw StoryInternalErrorException(MessageFormatter.format(ApplicationConstants.ERROR_MESSAGE_PERSON_DOESNOT_EXIST, storyDto.authorId).message)
        }
        if (pageService.notExists(storyDto.firstPageId!!)) {
            throw StoryInternalErrorException(MessageFormatter.format(ApplicationConstants.ERROR_MESSAGE_PAGE_DOESNOT_EXIST, storyDto.firstPageId).message)
        }
        if (storyService.notExists(storyDto.storyId!!)) {
            throw StoryInternalErrorException(MessageFormatter.format(ApplicationConstants.ERROR_MESSAGE_STORY_DOESNOT_EXIST, storyDto.storyId).message)
        }
        ResponseEntity.ok(storyService.createOrUpdate(storyDto))
    }

    @DeleteMapping(value = ["/{storyId}"])
    @ResponseBody
    fun deleteStory(request: HttpServletRequest?,
                    @PathVariable storyId: Int) {
        if (logger.isInfoEnabled) {
            logger.info(ApplicationConstants.CONTROLLER_CALL_LOG, request?.requestURI)
        }
        storyService.delete(storyId)
    }

}