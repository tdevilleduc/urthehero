package com.tdevilleduc.urthehero.back.service.impl;

import com.tdevilleduc.urthehero.back.dao.PageDao;
import com.tdevilleduc.urthehero.back.dao.PersonDao;
import com.tdevilleduc.urthehero.back.dao.ProgressionDao;
import com.tdevilleduc.urthehero.back.dao.StoryDao;
import com.tdevilleduc.urthehero.back.exceptions.PageNotFoundException;
import com.tdevilleduc.urthehero.back.exceptions.PersonNotFoundException;
import com.tdevilleduc.urthehero.back.exceptions.ProgressionNotFoundException;
import com.tdevilleduc.urthehero.back.exceptions.StoryNotFoundException;
import com.tdevilleduc.urthehero.back.model.Page;
import com.tdevilleduc.urthehero.back.model.Person;
import com.tdevilleduc.urthehero.back.model.Progression;
import com.tdevilleduc.urthehero.back.model.Story;
import com.tdevilleduc.urthehero.back.service.IProgressionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProgressionService implements IProgressionService {

    @Autowired
    private StoryDao storyDao;
    @Autowired
    private PersonDao personDao;
    @Autowired
    private PageDao pageDao;
    @Autowired
    private ProgressionDao progressionDao;

    @Override
    public Progression doProgressionAction(Integer personId, Integer storyId, Integer pageId) {

        // controle des parametres
        Optional<Story> story = storyDao.findById(storyId);
        if (story.isEmpty()) {
            throw new StoryNotFoundException("L'histoire avec l'id " + storyId + " n'existe pas");
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
        Progression progression = progressionDao.findByPersonIdAndStoryId(personId, storyId);
        if (progression == null) {
            throw new ProgressionNotFoundException("Aucune progression avec le personId " + personId + " et le storyId " + storyId);
        }

        // modification de la progression
        progression.setActualPageId(pageId);

        return progressionDao.save(progression);
    }


}
