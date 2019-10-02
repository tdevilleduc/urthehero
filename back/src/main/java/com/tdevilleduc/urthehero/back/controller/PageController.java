package com.tdevilleduc.urthehero.back.controller;

import com.tdevilleduc.urthehero.back.dao.PageDao;
import com.tdevilleduc.urthehero.back.dao.StoryDao;
import com.tdevilleduc.urthehero.back.exceptions.PageNotFoundException;
import com.tdevilleduc.urthehero.back.exceptions.StoryNotFoundException;
import com.tdevilleduc.urthehero.model.Page;
import com.tdevilleduc.urthehero.model.Story;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api( description = "API Page pour les interactions avec les pages" )
@RestController
public class PageController {

    @Autowired
    private PageDao pageDao;
    @Autowired
    private StoryDao storyDao;

    @ApiOperation( value = "Récupère une page à partir de son identifiant" )
    @GetMapping(value = "/Page/{pageId}")
    public Page getPageById(@PathVariable int pageId) {

        Page page = pageDao.findById(pageId);

        if (page == null) {
            throw new PageNotFoundException("La page avec l'id " + pageId + " n'existe pas");
        }

        Story story = page.getStory();

        if (story == null) {
            throw new PageNotFoundException("La page avec l'id " + pageId + " n'est pas associée à une histoire");
        }

        return page;
    }

    @ApiOperation( value = "Récupère la liste des pages d'une histoire" )
    @GetMapping(value = "/Pages/{storyId}")
    public List<Page> getPagesByStoryId(@PathVariable int storyId) {

        Story story = storyDao.findById(storyId);

        if (story == null) {
            throw new StoryNotFoundException("L'histoire avec l'id " + storyId + " n'existe pas");
        }

        return story.getPages();
    }
}
