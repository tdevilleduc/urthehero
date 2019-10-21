package com.tdevilleduc.urthehero.back.controller;

import com.tdevilleduc.urthehero.back.model.Page;
import com.tdevilleduc.urthehero.back.model.Story;
import com.tdevilleduc.urthehero.back.service.impl.PageService;
import com.tdevilleduc.urthehero.back.service.impl.StoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;

@Api(value = "Page", tags = { "Page Controller" } )
@RestController
@RequestMapping("/Page")
public class PageController {

    @Autowired
    private StoryService storyService;
    @Autowired
    private PageService pageService;

    @ApiOperation( value = "Récupère une page à partir de son identifiant" )
    @GetMapping(value = "/{pageId}")
    public Callable<ResponseEntity<Page>> getPageById(@PathVariable int pageId) {
        return () -> {
            Page page = pageService.findById(pageId);
            Story story = page.getStory();
            if (story == null) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(page);
        };
    }

    @ApiOperation( value = "Récupère la liste des pages d'une histoire" )
    @GetMapping(value = "/all/Story/{storyId}")
    public Callable<ResponseEntity<List<Page>>> getAllPagesByStoryId(@PathVariable int storyId) {
        return () -> {
            Optional<Story> optional = storyService.findById(storyId);
            return optional
                    .map(Story::getPages)
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        };
    }

    @ApiOperation( value = "Récupère la première page d'une histoire" )
    @GetMapping(value = "/Story/{storyId}")
    public Callable<ResponseEntity<Page>> getFirstPageByStoryId(@PathVariable int storyId) {
        return () -> {
            Optional<Story> optional = storyService.findById(storyId);
            return optional
                    .map(Story::getFirstPageId)
                    .map(firstPageId -> pageService.findById(firstPageId))
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        };
    }
}
