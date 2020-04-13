package com.tdevilleduc.urthehero.back.controller

import com.tdevilleduc.urthehero.back.constant.ApplicationConstants
import com.tdevilleduc.urthehero.back.exceptions.StoryInternalErrorException
import com.tdevilleduc.urthehero.back.model.Story
import com.tdevilleduc.urthehero.back.model.StoryDTO
import com.tdevilleduc.urthehero.back.service.IPageService
import com.tdevilleduc.urthehero.back.service.IStoryService
import com.tdevilleduc.urthehero.back.service.IUserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
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
internal class StoryController(private val storyService: IStoryService, private val userService: IUserService, private val pageService: IPageService) {

    @GetMapping(value = ["/all"], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "\${swagger.controller.story.get-all.value}", description = "\${swagger.controller.story.get-all.notes}")
    @ResponseBody
    fun getAllStories(request: HttpServletRequest): Callable<ResponseEntity<MutableList<Story>>> = Callable {
        ResponseEntity.ok(storyService.findAll())
    }

    @GetMapping(value = ["/{storyId}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "\${swagger.controller.story.get-by-id.value}", description = "\${swagger.controller.story.get-by-id.notes}")
    @ResponseBody
    fun getStoryById(request: HttpServletRequest,
                     @PathVariable storyId: Int): Callable<ResponseEntity<Story>> = Callable {
        ResponseEntity.ok(storyService.findById(storyId))
    }

    @PutMapping
    @ResponseBody
    fun createStory(request: HttpServletRequest,
                    @RequestBody storyDTO: StoryDTO): Callable<ResponseEntity<StoryDTO>> = Callable {
        //TODO: déplacer les controles dans le service ?
        Assert.notNull(storyDTO.authorId) { throw StoryInternalErrorException("L'auteur de l'histoire passée en paramètre ne peut pas être null") }
        Assert.notNull(storyDTO.firstPageId) { throw StoryInternalErrorException("La première page de l'histoire passée en paramètre ne peut pas être null") }
        if (userService.notExists(storyDTO.authorId!!)) {
            throw StoryInternalErrorException(MessageFormatter.format(ApplicationConstants.ERROR_MESSAGE_USER_DOESNOT_EXIST, storyDTO.authorId).message)
        }
        if (pageService.notExists(storyDTO.firstPageId!!)) {
            throw StoryInternalErrorException(MessageFormatter.format(ApplicationConstants.ERROR_MESSAGE_PAGE_DOESNOT_EXIST, storyDTO.firstPageId).message)
        }
        if (storyService.exists(storyDTO.storyId)) {
            throw StoryInternalErrorException(MessageFormatter.format("L'id {} existe déjà. Elle ne peut être créée", storyDTO.storyId).message)
        }
        ResponseEntity.ok(storyService.createOrUpdate(storyDTO))
    }

    @PostMapping
    @ResponseBody
    fun updateStory(request: HttpServletRequest,
                    @RequestBody storyDto: StoryDTO): Callable<ResponseEntity<StoryDTO>> = Callable {
        //TODO: déplacer les controles dans le service ?
        Assert.notNull(storyDto.authorId) { throw StoryInternalErrorException("L'auteur de l'histoire passée en paramètre ne peut pas être null") }
        Assert.notNull(storyDto.firstPageId) { throw StoryInternalErrorException("La première page de l'histoire passée en paramètre ne peut pas être null") }
        Assert.notNull(storyDto.storyId) { throw StoryInternalErrorException("L'identifiant de l'histoire passée en paramètre ne peut pas être null") }
        if (userService.notExists(storyDto.authorId!!)) {
            throw StoryInternalErrorException(MessageFormatter.format(ApplicationConstants.ERROR_MESSAGE_USER_DOESNOT_EXIST, storyDto.authorId).message)
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
                    @PathVariable storyId: Int) = Callable {
        storyService.delete(storyId)
    }

}