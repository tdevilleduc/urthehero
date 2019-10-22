package com.tdevilleduc.urthehero.back.controller;

import com.tdevilleduc.urthehero.back.exceptions.PageInternalErrorException;
import com.tdevilleduc.urthehero.back.exceptions.PageNotFoundException;
import com.tdevilleduc.urthehero.back.model.Page;
import com.tdevilleduc.urthehero.back.model.Story;
import com.tdevilleduc.urthehero.back.service.IPageService;
import com.tdevilleduc.urthehero.back.service.IStoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.junit.jupiter.api.Assertions;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Api(value = "Page", tags = { "Page Controller" } )
@RestController
@RequestMapping("/Page")
public class PageController {

    @Autowired
    private IStoryService storyService;
    @Autowired
    private IPageService pageService;

    @ApiOperation( value = "Récupère une page à partir de son identifiant" )
    @GetMapping(value = "/{pageId}")
    public Page getPageById(@PathVariable int pageId) {

        Page page = pageService.findById(pageId);
        Story story = page.getStory();
        if (story == null) {
            throw new PageNotFoundException("La page avec l'id " + pageId + " n'est pas associée à une histoire");
        }

        return page;
    }

    @ApiOperation( value = "Récupère la liste des pages d'une histoire" )
    @GetMapping(value = "/all/Story/{storyId}")
    public List<Page> getAllPagesByStoryId(@PathVariable int storyId) {
        Story story = storyService.findById(storyId);
        return story.getPages();
    }

    @ApiOperation( value = "Récupère la première page d'une histoire" )
    @GetMapping(value = "/Story/{storyId}")
    public Page getFirstPageByStoryId(@PathVariable int storyId) {

        Story story = storyService.findById(storyId);
        Integer firstPageId = story.getFirstPageId();

        return pageService.findById(firstPageId);
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
        Assertions.assertNotNull(page.getId(), () -> {
            throw new PageInternalErrorException("L'identifiant de la page passée en paramètre ne peut pas être null");
        });
        return pageService.createOrUpdate(page);
    }

    @DeleteMapping(value = "/{pageId}")
    public void deletePage(@PathVariable @NotNull Integer pageId) {
        pageService.delete(pageId);
    }
}
