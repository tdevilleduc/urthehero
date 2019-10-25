package com.tdevilleduc.urthehero.back.controller;

import com.tdevilleduc.urthehero.back.exceptions.PageInternalErrorException;
import com.tdevilleduc.urthehero.back.model.Page;
import com.tdevilleduc.urthehero.back.model.Story;
import com.tdevilleduc.urthehero.back.service.IPageService;
import com.tdevilleduc.urthehero.back.service.IStoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;

@Api(value = "Page", tags = { "Page Controller" } )
@RestController
@RequestMapping("/api/page")
public class PageController {

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
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = ""),
            @ApiResponse(code = 404, message = ""),
            @ApiResponse(code = 408, message = ""),
            @ApiResponse(code = 500, message = "")
    })
    public @ResponseBody Callable<ResponseEntity<Page>> getPageById(
            @PathVariable Integer pageId) {
        return () -> {
            Optional<Page> optional = pageService.findById(pageId);
            return optional
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        };
    }

    @ApiOperation( value = "Récupère la liste des pages d'une histoire" )
    @GetMapping(value = "/all/story/{storyId}")
    public @ResponseBody Callable<ResponseEntity<List<Page>>> getAllPagesByStoryId(
            @PathVariable Integer storyId) {
        return () -> {
            Optional<Story> optional = storyService.findById(storyId);
            return optional
                    .map(Story::getPages)
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        };
    }

    @ApiOperation( value = "Récupère la première page d'une histoire" )
    @GetMapping(value = "/story/{storyId}")
    public @ResponseBody Callable<ResponseEntity<Page>> getFirstPageByStoryId(
            @PathVariable int storyId) {
        return () -> {
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
    public Page createPage(@RequestBody @NotNull Page page) {
        if (page.getId() != null && pageService.exists(page.getId())) {
            throw new PageInternalErrorException(MessageFormatter.format("Une page avec l'identifiant {} existe déjà. Elle ne peut être créée", page.getId()).getMessage());
        }
        return pageService.createOrUpdate(page);
    }

    @PostMapping
    public Page updatePage(@RequestBody @NotNull Page page) {
        Assert.notNull(page.getId(), () -> {
            throw new PageInternalErrorException("L'identifiant de la page passée en paramètre ne peut pas être null");
        });
        return pageService.createOrUpdate(page);
    }

    @DeleteMapping(value = "/{pageId}")
    public void deletePage(@PathVariable @NotNull Integer pageId) {
        pageService.delete(pageId);
    }
}
