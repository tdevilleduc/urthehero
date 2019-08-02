package com.tdevilleduc.urthehero.story.web.controller;

import com.tdevilleduc.urthehero.story.dao.PageDao;
import com.tdevilleduc.urthehero.story.dao.StoryDao;
import com.tdevilleduc.urthehero.story.exceptions.StoryNotFoundException;
import com.tdevilleduc.urthehero.story.model.Story;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api( description = "API Story pour les interactions avec les histoires" )
@RestController
public class StoryController {

    @Autowired
    private PageDao pageDao;
    @Autowired
    private StoryDao storyDao;

    @ApiOperation( value = "Récupère la liste des histoires" )
    @GetMapping(value = "/Stories")
    public List<Story> getStories() {
        return storyDao.findAll();
    }

    @ApiOperation( value = "Récupère une histoire à partir de son identifiant" )
    @GetMapping(value = "/Story/{storyId}")
    public Story getStoryById(@PathVariable int storyId) {
        Story story = storyDao.findById(storyId);

        if (story == null) {
            throw new StoryNotFoundException("L'histoire avec l'id " + storyId + " n'existe pas");
        }

        return story;
    }


}
