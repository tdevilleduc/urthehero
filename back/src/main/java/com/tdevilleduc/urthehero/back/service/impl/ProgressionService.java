package com.tdevilleduc.urthehero.back.service.impl;

import com.tdevilleduc.urthehero.back.dao.PageDao;
import com.tdevilleduc.urthehero.back.dao.PersonDao;
import com.tdevilleduc.urthehero.back.dao.ProgressionDao;
import com.tdevilleduc.urthehero.back.exceptions.PageNotFoundException;
import com.tdevilleduc.urthehero.back.exceptions.PersonNotFoundException;
import com.tdevilleduc.urthehero.back.exceptions.ProgressionNotFoundException;
import com.tdevilleduc.urthehero.back.exceptions.StoryNotFoundException;
import com.tdevilleduc.urthehero.back.model.Page;
import com.tdevilleduc.urthehero.back.model.Person;
import com.tdevilleduc.urthehero.back.model.Progression;
import com.tdevilleduc.urthehero.back.model.Story;
import com.tdevilleduc.urthehero.back.service.IProgressionService;
import com.tdevilleduc.urthehero.back.service.IStoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class ProgressionService implements IProgressionService {

    @Autowired
    private IStoryService storyService;

    @Autowired
    private PersonDao personDao;
    @Autowired
    private PageDao pageDao;
    @Autowired
    private ProgressionDao progressionDao;

    @Override
    public Progression doProgressionAction(Integer personId, Integer storyId, Integer pageId) {

        // controle des parametres
        if (storyService.notExists(storyId)) {
            throw new StoryNotFoundException(String.format("L'histoire avec l'id {} n'existe pas", storyId));
        }

        Optional<Person> person = personDao.findById(personId);
        if (person.isEmpty()) {
            throw new PersonNotFoundException("L'utilisateur avec l'id "+ personId +" n'existe pas");
        }

        Optional<Page> newPage = pageDao.findById(pageId);
        if (newPage.isEmpty()) {
            throw new PageNotFoundException("La page avec l'id " + pageId + " n'existe pas");
        }

        Story storyFromPage = newPage.get().getStory();
        if (storyFromPage == null) {
            throw new PageNotFoundException("La page avec l'id " + pageId + " n'est pas associée à une histoire");
        }

        // recuperation de la progression
        Optional<Progression> optionalProgression = progressionDao.findByPersonIdAndStoryId(personId, storyId);
        if (optionalProgression.isEmpty()) {
            throw new ProgressionNotFoundException("Aucune progression avec le personId " + personId + " et le storyId " + storyId);
        }

        Progression progression = optionalProgression.get();

        // modification de la progression
        progression.setActualPageId(pageId);

        return progressionDao.save(progression);
    }

}
