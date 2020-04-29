package com.tdevilleduc.urthehero.back.controller

import com.tdevilleduc.urthehero.back.constant.ApplicationConstants
import com.tdevilleduc.urthehero.back.exceptions.PageInternalErrorException
import com.tdevilleduc.urthehero.back.model.Page
import com.tdevilleduc.urthehero.back.model.PageDTO
import com.tdevilleduc.urthehero.back.service.IPageService
import com.tdevilleduc.urthehero.back.service.IStoryService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.helpers.MessageFormatter
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.util.Assert
import org.springframework.web.bind.annotation.*
import java.util.concurrent.Callable

@Tag(name = "Page", description = "Page Controller")
@RestController
@RequestMapping("/api/page")
@SecurityRequirement(name = "bearerAuth")
internal class PageController(private val storyService: IStoryService, private val pageService: IPageService) {

    @GetMapping(value = ["/{pageId}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "\${swagger.controller.page.get-by-id.value}", description = "\${swagger.controller.page.get-by-id.notes}")
    fun getPageById(@PathVariable pageId: Int): Callable<ResponseEntity<Page>> = Callable {
        ResponseEntity.ok(pageService.findById(pageId))
    }

    @GetMapping(value = ["/story/{storyId}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "\${swagger.controller.page.get-first-by-story-id.value}", description = "\${swagger.controller.page.get-first-by-story-id.notes}")
    fun getFirstPageByStoryId(@PathVariable storyId: Int): Callable<ResponseEntity<Page>> = Callable {
        val story = storyService.findById(storyId)
        val firstPage = pageService.findById(story.firstPageId!!)
        ResponseEntity.ok(firstPage)
    }

    @PutMapping
    @Operation(summary = "\${swagger.controller.page.create.value}", description = "\${swagger.controller.page.create.notes}")
    fun createPage(@RequestBody page: PageDTO): Callable<ResponseEntity<PageDTO>> = Callable {
        if (pageService.exists(page.id)) {
            throw PageInternalErrorException(MessageFormatter.format(ApplicationConstants.ERROR_MESSAGE_PAGE_PAGEID_ALREADY_EXISTS, page.id).message)
        }
        ResponseEntity.ok(pageService.createOrUpdate(page))
    }

    @PostMapping
    @Operation(summary = "\${swagger.controller.page.update.value}", description = "\${swagger.controller.page.update.notes}")
    fun updatePage(@RequestBody pageDto: PageDTO): Callable<ResponseEntity<PageDTO>> = Callable {
        Assert.notNull(pageDto.id) { throw PageInternalErrorException(ApplicationConstants.ERROR_MESSAGE_PAGE_PAGEID_CANNOT_BE_NULL) }
        if (pageService.notExists(pageDto.id)) {
            throw PageInternalErrorException(MessageFormatter.format(ApplicationConstants.ERROR_MESSAGE_PAGE_DOESNOT_EXIST, pageDto.id).message)
        }
        ResponseEntity.ok(pageService.createOrUpdate(pageDto))
    }

    @DeleteMapping(value = ["/{pageId}"])
    @Operation(summary = "\${swagger.controller.page.delete.value}", description = "\${swagger.controller.page.delete.notes}")
    fun deletePage(@PathVariable pageId: Int) = Callable {
        pageService.delete(pageId)
    }

}