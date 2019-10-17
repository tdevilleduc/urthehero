package com.tdevilleduc.urthehero.back.controller;

import com.tdevilleduc.urthehero.back.exceptions.PageNotFoundException;
import com.tdevilleduc.urthehero.back.model.Page;
import com.tdevilleduc.urthehero.back.model.Story;
import com.tdevilleduc.urthehero.back.service.IPageService;
import com.tdevilleduc.urthehero.back.service.IStoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
