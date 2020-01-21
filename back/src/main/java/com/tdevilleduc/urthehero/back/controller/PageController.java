package com.tdevilleduc.urthehero.back.controller;

import com.tdevilleduc.urthehero.back.exceptions.PageInternalErrorException;
import com.tdevilleduc.urthehero.back.model.Page;
import com.tdevilleduc.urthehero.back.model.PageDTO;
import com.tdevilleduc.urthehero.back.model.Story;
import com.tdevilleduc.urthehero.back.service.IPageService;
import com.tdevilleduc.urthehero.back.service.IStoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.concurrent.Callable;

@Slf4j
@Api(value = "Page", tags = { "Page Controller" } )
@RestController
@RequestMapping("/api/page")
class PageController {

    private final IStoryService storyService;
    private final IPageService pageService;

    public PageController(IStoryService storyService, IPageService pageService) {
        this.storyService = storyService;
        this.pageService = pageService;
    }

    @GetMapping(value = "/{pageId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(
            value = "${swagger.controller.page.get-by-id.value}",
            notes = "${swagger.controller.page.get-by-id.notes}")
    public @ResponseBody Callable<ResponseEntity<Page>> getPageById(HttpServletRequest request,
                                                                    @PathVariable Integer pageId) {
        return () -> {
            if (log.isInfoEnabled()) {
                log.info("call: {}", request.getRequestURI());
            }
            Optional<Page> optional = pageService.findById(pageId);
            return optional
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        };
    }

    @GetMapping(value = "/story/{storyId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(
            value = "${swagger.controller.page.get-first-by-story-id.value}",
            notes = "${swagger.controller.page.get-first-by-story-id.notes}")
    public @ResponseBody Callable<ResponseEntity<Page>> getFirstPageByStoryId(HttpServletRequest request,
                                                                              @PathVariable int storyId) {
        return () -> {
            if (log.isInfoEnabled()) {
                log.info("call: {}", request.getRequestURI());
            }
            Optional<Story> optional = storyService.findById(storyId);
            return optional
                    .map(Story::getFirstPageId)
                    .map(pageService::findById)
                    .map(Optional::get)
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        };
    }

    @PutMapping
    public @ResponseBody Callable<ResponseEntity<PageDTO>> createPage(HttpServletRequest request,
                           @RequestBody @NotNull PageDTO page) {
        return () -> {
            if (log.isInfoEnabled()) {
                log.info("call: {}", request.getRequestURI());
            }
            if (page.getId() != null && pageService.exists(page.getId())) {
                throw new PageInternalErrorException(MessageFormatter.format("Une page avec l'identifiant {} existe déjà. Elle ne peut être créée", page.getId()).getMessage());
            }
            return ResponseEntity.ok(pageService.createOrUpdate(page));
        };
    }

    @PostMapping
    public @ResponseBody Callable<ResponseEntity<PageDTO>> updatePage(HttpServletRequest request,
                           @RequestBody @NotNull PageDTO page) {
        return () -> {
            if (log.isInfoEnabled()) {
                log.info("call: {}", request.getRequestURI());
            }
            Assert.notNull(page.getId(), () -> {
                throw new PageInternalErrorException("L'identifiant de la page passée en paramètre ne peut pas être null");
            });
            return ResponseEntity.ok(pageService.createOrUpdate(page));
        };
    }

    @DeleteMapping(value = "/{pageId}")
    public @ResponseBody void deletePage(HttpServletRequest request,
                           @PathVariable @NotNull Integer pageId) {
        if (log.isInfoEnabled()) {
            log.info("call: {}", request.getRequestURI());
        }
        pageService.delete(pageId);
    }
}
