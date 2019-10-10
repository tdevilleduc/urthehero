package com.tdevilleduc.urthehero.back.service.impl;

import com.tdevilleduc.urthehero.back.dao.StoryDao;
import com.tdevilleduc.urthehero.back.exceptions.StoryNotFoundException;
import com.tdevilleduc.urthehero.back.model.Story;
import com.tdevilleduc.urthehero.back.service.IStoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class StoryService implements IStoryService {

    @Autowired
    private StoryDao storyDao;

    public boolean exists(Integer storyId) {
        Optional<Story> story = storyDao.findById(storyId);
        if (story.isEmpty()) {
            log.error("L'histoire avec l'id {} n'existe pas", storyId);
            return false;
        }
        return true;
    }

    public boolean notExists(Integer storyId) {
        return ! exists(storyId);
    }

    public Story findById(Integer storyId) {
        Optional<Story> optionalStory = storyDao.findById(storyId);
        if (optionalStory.isEmpty()) {
            throw new StoryNotFoundException("L'histoire avec l'id " + storyId + " n'existe pas");
        }

        Story story = optionalStory.get();
        story.setNumberOfPages(story.getPages().size());

        return story;
    }

    public List<Story> findAll() {
        return storyDao.findAll().stream().map(story -> {
            story.setNumberOfPages(story.getPages().size());
            return story;
        }).collect(Collectors.toList());
    }
}
