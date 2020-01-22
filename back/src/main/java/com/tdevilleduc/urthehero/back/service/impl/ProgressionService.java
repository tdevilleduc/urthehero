package com.tdevilleduc.urthehero.back.service.impl;

import com.tdevilleduc.urthehero.back.dao.ProgressionDao;
import com.tdevilleduc.urthehero.back.exceptions.PageNotFoundException;
import com.tdevilleduc.urthehero.back.exceptions.PersonNotFoundException;
import com.tdevilleduc.urthehero.back.exceptions.ProgressionNotFoundException;
import com.tdevilleduc.urthehero.back.exceptions.StoryNotFoundException;
import com.tdevilleduc.urthehero.back.model.Page;
import com.tdevilleduc.urthehero.back.model.Progression;
import com.tdevilleduc.urthehero.back.service.IPageService;
import com.tdevilleduc.urthehero.back.service.IPersonService;
import com.tdevilleduc.urthehero.back.service.IProgressionService;
import com.tdevilleduc.urthehero.back.service.IStoryService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.tdevilleduc.urthehero.back.config.ResilienceConstants.INSTANCE_PROGRESSION_SERVICE;
import static com.tdevilleduc.urthehero.back.constant.ApplicationConstants.*;

@Slf4j
@Service
public class ProgressionService implements IProgressionService {

    private static final Long ZERO = 0L;

    @Autowired
    private IStoryService storyService;
    @Autowired
    private IPersonService personService;
    @Autowired
    private IPageService pageService;
    @Autowired
    private ProgressionDao progressionDao;

    public Progression doProgressionAction(final Integer personId, final Integer storyId, final Integer pageId) {
        Assert.notNull(personId, CHECK_PERSONID_PARAMETER_MANDATORY);
        Assert.notNull(storyId, CHECK_STORYID_PARAMETER_MANDATORY);
        Assert.notNull(pageId, CHECK_PAGEID_PARAMETER_MANDATORY);

        if (personService.notExists(personId)) {
            throw new PersonNotFoundException(MessageFormatter.format(ERROR_MESSAGE_PERSON_DOESNOT_EXIST, personId).getMessage());
        }
        if (storyService.notExists(storyId)) {
            throw new StoryNotFoundException(MessageFormatter.format(ERROR_MESSAGE_STORY_DOESNOT_EXIST, storyId).getMessage());
        }
        if (pageService.notExists(pageId)) {
            throw new PageNotFoundException(MessageFormatter.format(ERROR_MESSAGE_PAGE_DOESNOT_EXIST, pageId).getMessage());
        }

        Optional<Progression> optionalProgression = progressionDao.findByPersonIdAndStoryId(personId, storyId);
        if (optionalProgression.isPresent()) {
            Progression progression = optionalProgression.get();

            // TODO: vérifier qu'on a le droit d'avancer sur cette page depuis la page précédente
            progression.setActualPageId(pageId);

            return progressionDao.save(progression);
        } else {
            throw new ProgressionNotFoundException("Aucune progression avec le personId " + personId + " et le storyId " + storyId);
        }
    }

    @CircuitBreaker(name = INSTANCE_PROGRESSION_SERVICE, fallbackMethod = "emptyProgressionList")
    public List<Progression> findByPersonId(final Integer personId) {
        Assert.notNull(personId, CHECK_PERSONID_PARAMETER_MANDATORY);
        if (personService.notExists(personId)) {
            throw new PersonNotFoundException(MessageFormatter.format(ERROR_MESSAGE_PERSON_DOESNOT_EXIST, personId).getMessage());
        }

        return progressionDao.findByPersonId(personId);
    }

    @SuppressWarnings({"squid:UnusedPrivateMethod", "squid:S1172"})
    private List<Progression> emptyProgressionList(final Integer personId, final Throwable e) {
        return Collections.emptyList();
    }

    @CircuitBreaker(name = INSTANCE_PROGRESSION_SERVICE, fallbackMethod = "emptyProgression")
    public Optional<Progression> findByPersonIdAndStoryId(final Integer personId, final Integer storyId) {
        Assert.notNull(personId, CHECK_PERSONID_PARAMETER_MANDATORY);
        Assert.notNull(storyId, CHECK_STORYID_PARAMETER_MANDATORY);
        if (personService.notExists(personId)) {
            throw new PersonNotFoundException(MessageFormatter.format(ERROR_MESSAGE_PERSON_DOESNOT_EXIST, personId).getMessage());
        }
        if (storyService.notExists(storyId)) {
            throw new StoryNotFoundException(MessageFormatter.format(ERROR_MESSAGE_STORY_DOESNOT_EXIST, storyId).getMessage());
        }

        return progressionDao.findByPersonIdAndStoryId(personId, storyId);
    }

    @SuppressWarnings({"squid:UnusedPrivateMethod", "squid:S1172"})
    private Optional<Page> emptyProgression(final Integer personId, final Integer storyId, final Throwable e) {
        return Optional.empty();
    }

    @CircuitBreaker(name = INSTANCE_PROGRESSION_SERVICE, fallbackMethod = "emptyProgressionCount")
    public Long countByStoryId(final Integer storyId) {
        Assert.notNull(storyId, CHECK_STORYID_PARAMETER_MANDATORY);
        if (storyService.notExists(storyId)) {
            throw new StoryNotFoundException(MessageFormatter.format(ERROR_MESSAGE_STORY_DOESNOT_EXIST, storyId).getMessage());
        }

        return progressionDao.countByStoryId(storyId);
    }

    @SuppressWarnings({"squid:UnusedPrivateMethod", "squid:S1172"})
    private Long emptyProgressionCount(final Integer storyId, final Throwable e) {
        return ZERO;
    }
}
