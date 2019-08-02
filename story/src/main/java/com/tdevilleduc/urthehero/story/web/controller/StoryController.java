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
public class StoryController {

    @Autowired
    private PageDao pageDao;
    @Autowired
    private StoryDao storyDao;

    @GetMapping(value="/Story/{storyId}/Page/{pageId}")
    public Page getPageById(@PathVariable int storyId, @PathVariable int pageId) {
        Story story = storyDao.findById(storyId);

        if (story == null) {
            throw new StoryNotFoundException("L'histoire avec l'id "+storyId+" n'existe pas");
        }

        Page page = pageDao.findById(pageId);

        if (page == null) {
            throw new PageNotFoundException("La page avec l'id "+pageId+" n'existe pas");
        }
        if (page.getStoryId() != storyId) {
            throw new PageNotFoundException("La page avec l'id "+pageId+" ne fait pas partie de l'histoire "+storyId);
        }

        return page;
    }


    @GetMapping(value="/Story/{storyId}/FirstPage")
    public Page getFirstPageByStoryId(@PathVariable int storyId) {
        Story story = storyDao.findById(storyId);

        if (story == null) {
            throw new StoryNotFoundException("L'histoire avec l'id "+storyId+" n'existe pas");
        }

        Page page = pageDao.findById(story.getFirstPageId().intValue());

        if (page == null) {
            throw new PageNotFoundException("La page avec l'id "+storyId+" n'existe pas");
        }
        if (page.getStoryId() != storyId) {
            throw new PageNotFoundException("La page avec l'id "+page.getId()+" ne fait pas partie de l'histoire "+storyId);
        }

        return page;
    }


}
