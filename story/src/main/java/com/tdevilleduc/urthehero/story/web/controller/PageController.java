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

        Integer storyId = page.getStoryId();

        if (storyId == null) {
            throw new PageNotFoundException("La page avec l'id " + pageId + " n'est pas associée à une histoire");
        }

        Story story = storyDao.findById(storyId.intValue());

        if (story == null) {
            throw new StoryNotFoundException("L'histoire avec l'id " + storyId + " n'existe pas");
        }

        return page;
    }


    @GetMapping(value = "/FirstPage/{storyId}")
    public Page getFirstPageByStoryId(@PathVariable int storyId) {
        Story story = storyDao.findById(storyId);

        if (story == null) {
            throw new StoryNotFoundException("L'histoire avec l'id " + storyId + " n'existe pas");
        }

        Integer firstPageId = story.getFirstPageId();

        if (firstPageId == null) {
            throw new StoryNotFoundException("L'histoire avec l'id " + storyId + " n'a pas de page");
        }

        Page page = pageDao.findById(firstPageId.intValue());

        if (page == null) {
            throw new PageNotFoundException("La page avec l'id " + storyId + " n'existe pas");
        }
        if (page.getStoryId() != storyId) {
            throw new PageNotFoundException("La page avec l'id " + page.getId() + " ne fait pas partie de l'histoire " + storyId);
        }

        return page;
    }
}
