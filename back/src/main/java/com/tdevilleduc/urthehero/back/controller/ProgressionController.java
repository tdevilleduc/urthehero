package com.tdevilleduc.urthehero.back.controller;

import com.tdevilleduc.urthehero.back.dao.PersonDao;
import com.tdevilleduc.urthehero.back.dao.ProgressionDao;
import com.tdevilleduc.urthehero.back.dao.StoryDao;
import com.tdevilleduc.urthehero.back.exceptions.PersonNotFoundException;
import com.tdevilleduc.urthehero.back.exceptions.ProgressionNotFoundException;
import com.tdevilleduc.urthehero.back.exceptions.StoryNotFoundException;
import com.tdevilleduc.urthehero.back.model.Person;
import com.tdevilleduc.urthehero.back.model.Progression;
import com.tdevilleduc.urthehero.back.model.Story;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api( value = "API users pour les interactions avec l'avancement des histoires" )
@RestController
@RequestMapping("/Progression")
public class ProgressionController {

    @Autowired
    private PersonDao personDao;
    @Autowired
    private StoryDao storyDao;
    @Autowired
    private ProgressionDao progressionDao;

    @ApiOperation( value = "Récupère la liste des histoires en cours d'une personne" )
    @GetMapping(value="/Person/{personId}/all")
    public List<Progression> getAllByPersonId(@PathVariable int personId) {
        Person person = personDao.findById(personId);

        if (person == null) {
            throw new PersonNotFoundException("L'utilisateur avec l'id "+ personId +" n'existe pas");
        }

        return progressionDao.findByPersonId(personId);
    }

    @ApiOperation( value = "Récupère la progression d'une personne sur une histoire" )
    @GetMapping(value="Person/{personId}/Story/{storyId}")
    public Progression getOneByPersonIdAndStoryId(@PathVariable int personId, @PathVariable int storyId) {
        Person person = personDao.findById(personId);

        if (person == null) {
            throw new PersonNotFoundException("L'utilisateur avec l'id "+ personId +" n'existe pas");
        }

        Story story = storyDao.findById(storyId);

        if (story == null) {
            throw new StoryNotFoundException("L'histoire avec l'id " + storyId + " n'existe pas");
        }

        return progressionDao.findByPersonIdAndStoryId(personId, storyId);
    }

    @ApiOperation( value = "Met à jour la progression d'une personne sur une histoire avec une page définie" )
    @PostMapping(value="Person/{personId}/Story/{storyId}/Page/{pageId}")
    public Progression play(@PathVariable int personId, @PathVariable int storyId, @PathVariable int newPageId) {
        Person person = personDao.findById(personId);

        if (person == null) {
            throw new PersonNotFoundException("L'utilisateur avec l'id "+ personId +" n'existe pas");
        }

        Story story = storyDao.findById(storyId);

        if (story == null) {
            throw new StoryNotFoundException("L'histoire avec l'id " + storyId + " n'existe pas");
        }

        Progression progression = progressionDao.findByPersonIdAndStoryId(personId, storyId);

        if (progression == null) {
            throw new ProgressionNotFoundException("La progression avec le personId " + personId + " et le storyId " + storyId + " n'existe pas");
        }

        progression.setActualPageId(newPageId);

        return progressionDao.save(progression);
    }


}