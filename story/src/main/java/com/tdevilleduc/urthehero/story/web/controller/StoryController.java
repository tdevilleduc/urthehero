package com.tdevilleduc.urthehero.story.web.controller;

import com.tdevilleduc.urthehero.story.dao.PageDao;
import com.tdevilleduc.urthehero.story.dao.StoryDao;
import com.tdevilleduc.urthehero.story.exceptions.StoryNotFoundException;
import com.tdevilleduc.urthehero.story.model.Story;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StoryController {

    @Autowired
    private PageDao pageDao;
    @Autowired
    private StoryDao storyDao;

    @GetMapping(value = "/Story/{storyId}")
    public Story getStoryById(@PathVariable int storyId) {
        Story story = storyDao.findById(storyId);

        if (story == null) {
            throw new StoryNotFoundException("L'histoire avec l'id " + storyId + " n'existe pas");
        }

        return story;
    }


}
