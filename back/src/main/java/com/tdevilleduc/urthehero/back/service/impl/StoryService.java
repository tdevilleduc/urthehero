package com.tdevilleduc.urthehero.back.service.impl;

import com.tdevilleduc.urthehero.back.dao.ProgressionDao;
import com.tdevilleduc.urthehero.back.dao.StoryDao;
import com.tdevilleduc.urthehero.back.exceptions.StoryNotFoundException;
import com.tdevilleduc.urthehero.back.model.Progression;
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
    @Autowired
    private ProgressionDao progressionDao;

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
        story = fillStoryWithNumberOfPages(story);
        story = fillStoryWithNumberOfReaders(story);

        return story;
    }

    public List<Story> findAll() {
        return storyDao.findAll().stream()
                .map(this::fillStoryWithNumberOfPages)
                .map(this::fillStoryWithNumberOfReaders)
                .collect(Collectors.toList());
    }

    public List<Story> findByPersonId(Integer personId) {
        List<Progression> progressionList = progressionDao.findByPersonId(personId);
        return progressionList.stream()
                .map(this::getStoryFromProgression)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(this::fillStoryWithNumberOfPages)
                .map(this::fillStoryWithNumberOfReaders)
                .collect(Collectors.toList());
    }

    private Optional<Story> getStoryFromProgression(Progression progression) {
        Optional<Story> optionalStory = storyDao.findById(progression.getStoryId());
        optionalStory.ifPresent(story -> {
            story.setCurrentPageId(progression.getActualPageId());
        });
        return optionalStory;
    }

    private Story fillStoryWithNumberOfReaders(Story story) {
        Long numberOfReaders = progressionDao.countByStoryId(story.getId());
        story.setNumberOfReaders(numberOfReaders);
        return story;
    }

    private Story fillStoryWithNumberOfPages(Story story) {
        story.setNumberOfPages(Long.valueOf(story.getPages().size()));
        return story;
    }
}
