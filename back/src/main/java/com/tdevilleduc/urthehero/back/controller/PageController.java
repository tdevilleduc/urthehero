package com.tdevilleduc.urthehero.back.controller;

import com.tdevilleduc.urthehero.back.dao.PageDao;
import com.tdevilleduc.urthehero.back.exceptions.PageNotFoundException;
import com.tdevilleduc.urthehero.back.model.Page;
import com.tdevilleduc.urthehero.back.model.Story;
import com.tdevilleduc.urthehero.back.service.impl.StoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Api(value = "Page", tags = { "Page Controller" } )
@RestController
@RequestMapping("/Page")
public class PageController {

    @Autowired
    private PageDao pageDao;

    @Autowired
    private StoryService storyService;

    @ApiOperation( value = "Récupère une page à partir de son identifiant" )
    @GetMapping(value = "/{pageId}")
    public Page getPageById(@PathVariable int pageId) {

        Optional<Page> page = pageDao.findById(pageId);
        if (page.isEmpty()) {
            throw new PageNotFoundException("La page avec l'id " + pageId + " n'existe pas");
        }

        Story story = page.get().getStory();
        if (story == null) {
            throw new PageNotFoundException("La page avec l'id " + pageId + " n'est pas associée à une histoire");
        }

        return page.get();
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

        Optional<Page> page = pageDao.findById(firstPageId);
        if (page.isEmpty()) {
            throw new PageNotFoundException("La page avec l'id " + firstPageId + " n'existe pas");
        }

        return page.get();
    }
}
