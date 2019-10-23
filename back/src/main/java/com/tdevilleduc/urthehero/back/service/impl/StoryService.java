package com.tdevilleduc.urthehero.back.service.impl;

import com.tdevilleduc.urthehero.back.dao.StoryDao;
import com.tdevilleduc.urthehero.back.exceptions.StoryInternalErrorException;
import com.tdevilleduc.urthehero.back.model.Person;
import com.tdevilleduc.urthehero.back.model.Progression;
import com.tdevilleduc.urthehero.back.model.Story;
import com.tdevilleduc.urthehero.back.service.IPageService;
import com.tdevilleduc.urthehero.back.service.IPersonService;
import com.tdevilleduc.urthehero.back.service.IProgressionService;
import com.tdevilleduc.urthehero.back.service.IStoryService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class StoryService implements IStoryService {

    @Autowired
    private IPersonService personService;
    @Autowired
    private IPageService pageService;
    @Autowired
    private IProgressionService progressionService;

    @Autowired
    private StoryDao storyDao;

    public boolean exists(Integer storyId) {
        Assert.notNull(storyId, "The story parameter is mandatory !");
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

    @CircuitBreaker(name = "storyFindById", fallbackMethod = "emptyStory")
    public Optional<Story> findById(Integer storyId) {
        Assert.notNull(storyId, "The storyId parameter is mandatory !");
        return storyDao.findById(storyId)
                .map(this::fillStoryWithNumberOfPages)
                .map(this::fillStoryWithNumberOfReaders);
    }

    private Optional<Story> emptyStory(Integer storyId, Exception e) {
        log.error("Cannot retrieve story", e);
        return Optional.empty();
    }

    @Retry(name = "story_findAll", fallbackMethod = "emptyStoryList")
    public List<Story> findAll() {
        return storyDao.findAll().stream()
                .map(this::fillStoryWithNumberOfPages)
                .map(this::fillStoryWithNumberOfReaders)
                .collect(Collectors.toList());
    }

    private List<Story> emptyStoryList(Throwable e) {
        return Collections.emptyList();
    }

    @CircuitBreaker(name = "story_findByPersonId")
//    @Retry(name = "story_findByPersonId")
    public List<Story> findByPersonId(Integer personId) {
        Assert.notNull(personId, "The personId parameter is mandatory !");
        List<Progression> progressionList = progressionService.findByPersonId(personId);
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
        optionalStory.ifPresent(story -> story.setCurrentPageId(progression.getActualPageId()));
        return optionalStory;
    }

    private Story fillStoryWithNumberOfReaders(Story story) {
        Long numberOfReaders = progressionService.countByStoryId(story.getId());
        story.setNumberOfReaders(numberOfReaders);
        return story;
    }

    private Story fillStoryWithNumberOfPages(Story story) {
        story.setNumberOfPages((long) story.getPages().size());
        return story;
    }

    public Story createOrUpdate(Story story) {
        return storyDao.save(story);
    }

    public void delete(Integer storyId) {
        Assert.notNull(storyId, "The storyId parameter is mandatory !");
        Optional<Story> optional = findById(storyId);
        optional
            .ifPresentOrElse(story -> storyDao.delete(story),
                () -> {
                    throw new StoryInternalErrorException(MessageFormatter.format("L'histoire avec l'id {} n'existe pas", storyId).getMessage());
                }
        );
    }
}
