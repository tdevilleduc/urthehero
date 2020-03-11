package com.tdevilleduc.urthehero.back.controller

import com.tdevilleduc.urthehero.back.constant.ApplicationConstants
import com.tdevilleduc.urthehero.back.exceptions.PageInternalErrorException
import com.tdevilleduc.urthehero.back.model.Page
import com.tdevilleduc.urthehero.back.model.PageDTO
import com.tdevilleduc.urthehero.back.service.IPageService
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

@Tag(name = "Page", description = "Page Controller")
@RestController
@RequestMapping("/api/page")
internal class PageController(private val storyService: IStoryService, private val pageService: IPageService) {
    val logger: Logger = LoggerFactory.getLogger(PageController::class.java)

    @GetMapping(value = ["/{pageId}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "\${swagger.controller.page.get-by-id.value}", description = "\${swagger.controller.page.get-by-id.notes}")
    @ResponseBody
    fun getPageById(request: HttpServletRequest,
                    @PathVariable pageId: Int): Callable<ResponseEntity<Page>> = Callable {
        if (logger.isInfoEnabled) {
            logger.info(ApplicationConstants.CONTROLLER_CALL_LOG, request.requestURI)
        }
        val page = pageService.findById(pageId)
        ResponseEntity.ok(page)
    }

    @GetMapping(value = ["/story/{storyId}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "\${swagger.controller.page.get-first-by-story-id.value}", description = "\${swagger.controller.page.get-first-by-story-id.notes}")
    @ResponseBody
    fun getFirstPageByStoryId(request: HttpServletRequest,
                              @PathVariable storyId: Int): Callable<ResponseEntity<Page>> = Callable {
        if (logger.isInfoEnabled) {
            logger.info(ApplicationConstants.CONTROLLER_CALL_LOG, request.requestURI)
        }
        val story = storyService.findById(storyId)
        val firstPage = pageService.findById(story.firstPageId!!)
        ResponseEntity.ok(firstPage)
    }

    @PutMapping
    @ResponseBody
    fun createPage(request: HttpServletRequest,
                   @RequestBody page: PageDTO): Callable<ResponseEntity<PageDTO>> = Callable {
        if (logger.isInfoEnabled) {
            logger.info(ApplicationConstants.CONTROLLER_CALL_LOG, request.requestURI)
        }
        if (pageService.exists(page.id)) {
            throw PageInternalErrorException(MessageFormatter.format("Une page avec l'identifiant {} existe déjà. Elle ne peut être créée", page.id).message)
        }
        ResponseEntity.ok(pageService.createOrUpdate(page))
    }

    @PostMapping
    @ResponseBody
    fun updatePage(request: HttpServletRequest,
                   @RequestBody page: PageDTO): Callable<ResponseEntity<PageDTO>> = Callable {
        if (logger.isInfoEnabled) {
            logger.info(ApplicationConstants.CONTROLLER_CALL_LOG, request.requestURI)
        }
        Assert.notNull(page.id) { throw PageInternalErrorException("L'identifiant de la page passée en paramètre ne peut pas être null") }
        ResponseEntity.ok(pageService.createOrUpdate(page))
    }

    @DeleteMapping(value = ["/{pageId}"])
    @ResponseBody
    fun deletePage(request: HttpServletRequest,
                   @PathVariable pageId: Int) {
        if (logger.isInfoEnabled) {
            logger.info(ApplicationConstants.CONTROLLER_CALL_LOG, request.requestURI)
        }
        pageService.delete(pageId)
    }

}