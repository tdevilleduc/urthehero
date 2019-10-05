package com.tdevilleduc.urthehero.back.controller;

import com.tdevilleduc.urthehero.back.dao.PageDao;
import com.tdevilleduc.urthehero.back.dao.StoryDao;
import com.tdevilleduc.urthehero.back.exceptions.PageNotFoundException;
import com.tdevilleduc.urthehero.back.exceptions.StoryNotFoundException;
import com.tdevilleduc.urthehero.back.model.Page;
import com.tdevilleduc.urthehero.back.model.Story;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@Api( value = "API Page pour les interactions avec les pages" )
@RestController
@RequestMapping("/Page")
public class PageController {

    @Autowired
    private PageDao pageDao;
    @Autowired
    private StoryDao storyDao;

    @ApiOperation( value = "Récupère une page à partir de son identifiant" )
    @GetMapping(value = "/{pageId}", produces = APPLICATION_JSON_UTF8_VALUE)
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
    @GetMapping(value = "/all/Story/{storyId}", produces = APPLICATION_JSON_UTF8_VALUE)
    public List<Page> getAllPagesByStoryId(@PathVariable int storyId) {

        Story story = storyDao.findById(storyId);
        if (story == null) {
            throw new StoryNotFoundException("L'histoire avec l'id " + storyId + " n'existe pas");
        }

        return story.getPages();
    }

    @ApiOperation( value = "Récupère la première page d'une histoire" )
    @GetMapping(value = "/Story/{storyId}", produces = APPLICATION_JSON_UTF8_VALUE)
    public Page getFirstPageByStoryId(@PathVariable int storyId) {

        Story story = storyDao.findById(storyId);
        if (story == null) {
            throw new StoryNotFoundException("L'histoire avec l'id " + storyId + " n'existe pas");
        }

        Integer pageId = story.getFirstPageId();

        Optional<Page> page = pageDao.findById(pageId);
        if (page.isEmpty()) {
            throw new PageNotFoundException("La page avec l'id " + pageId + " n'existe pas");
        }

        return page.get();
    }
}
