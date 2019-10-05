package com.tdevilleduc.urthehero.back.service.impl;

import com.tdevilleduc.urthehero.back.dao.StoryDao;
import com.tdevilleduc.urthehero.back.model.Story;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class StoryService {

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
}
