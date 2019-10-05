package com.tdevilleduc.urthehero.back.controller;

import com.tdevilleduc.urthehero.back.dao.PersonDao;
import com.tdevilleduc.urthehero.back.dao.ProgressionDao;
import com.tdevilleduc.urthehero.back.exceptions.PersonNotFoundException;
import com.tdevilleduc.urthehero.back.exceptions.ProgressionNotFoundException;
import com.tdevilleduc.urthehero.back.exceptions.StoryNotFoundException;
import com.tdevilleduc.urthehero.back.model.Person;
import com.tdevilleduc.urthehero.back.model.Progression;
import com.tdevilleduc.urthehero.back.service.IProgressionService;
import com.tdevilleduc.urthehero.back.service.impl.ProgressionService;
import com.tdevilleduc.urthehero.back.service.impl.StoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Api(value = "Progression", tags = { "Progression Controller" } )
@RestController
@RequestMapping("/Progression")
public class ProgressionController {

    @Autowired
    private IProgressionService progressionService;
    @Autowired
    private StoryService storyService;

    @Autowired
    private PersonDao personDao;
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

        if (storyService.notExists(storyId)) {
            throw new StoryNotFoundException(String.format("L'histoire avec l'id {} n'existe pas", storyId));
        }

        Optional<Progression> progression = progressionDao.findByPersonIdAndStoryId(personId, storyId);
        if (progression.isEmpty()) {
            throw new ProgressionNotFoundException("Aucune progression avec le personId " + personId + " et le storyId " + storyId);
        }

        return progression.get();
    }

    @ApiOperation( value = "Met à jour la progression d'une personne sur une histoire avec une page définie" )
    @PostMapping(value="Person/{personId}/Story/{storyId}/Page/{newPageId}")
    public Progression postProgressionAction(@PathVariable int personId, @PathVariable int storyId, @PathVariable int newPageId) {
        return progressionService.doProgressionAction(personId, storyId, newPageId);
    }


}