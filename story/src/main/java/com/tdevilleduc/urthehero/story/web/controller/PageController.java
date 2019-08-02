package com.tdevilleduc.urthehero.story.web.controller;

import com.tdevilleduc.urthehero.story.dao.PageDao;
import com.tdevilleduc.urthehero.story.dao.StoryDao;
import com.tdevilleduc.urthehero.story.exceptions.PageNotFoundException;
import com.tdevilleduc.urthehero.story.exceptions.StoryNotFoundException;
import com.tdevilleduc.urthehero.story.model.Page;
import com.tdevilleduc.urthehero.story.model.Story;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PageController {

    @Autowired
    private PageDao pageDao;
    @Autowired
    private StoryDao storyDao;

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

    @GetMapping(value = "/Pages/{storyId}")
    public List<Page> getPagesByStoryId(@PathVariable int storyId) {

        Story story = storyDao.findById(storyId);

        if (story == null) {
            throw new StoryNotFoundException("L'histoire avec l'id " + storyId + " n'existe pas");
        }

        return story.getPages();
    }
}
