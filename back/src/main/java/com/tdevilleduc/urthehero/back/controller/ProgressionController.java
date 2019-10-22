package com.tdevilleduc.urthehero.back.controller;

import com.tdevilleduc.urthehero.back.dao.ProgressionDao;
import com.tdevilleduc.urthehero.back.model.Progression;
import com.tdevilleduc.urthehero.back.service.IProgressionService;
import com.tdevilleduc.urthehero.back.service.IStoryService;
import com.tdevilleduc.urthehero.back.service.impl.PersonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;

@Api(value = "Progression", tags = { "Progression Controller" } )
@RestController
@RequestMapping("/api/progression")
public class ProgressionController {

    @Autowired
    private IProgressionService progressionService;
    @Autowired
    private IStoryService storyService;
    @Autowired
    private PersonService personService;

    // TODO: remove this dependency
    @Autowired
    private ProgressionDao progressionDao;

    @ApiOperation( value = "Récupère la liste des histoires en cours d'une personne" )
    @GetMapping(value="/person/{personId}/all")
    public Callable<ResponseEntity<List<Progression>>> getAllByPersonId(@PathVariable int personId) {
        return () -> {
            if (personService.notExists(personId)) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(progressionDao.findByPersonId(personId));
        };
    }

    @ApiOperation( value = "Récupère la progression d'une personne sur une histoire" )
    @GetMapping(value="/person/{personId}/story/{storyId}")
    public Callable<ResponseEntity<Progression>> getOneByPersonIdAndStoryId(@PathVariable int personId, @PathVariable int storyId) {
        return () -> {
            if (personService.notExists(personId)) {
                return ResponseEntity.notFound().build();
            }

            if (storyService.notExists(storyId)) {
                return ResponseEntity.notFound().build();
            }

            Optional<Progression> progression = progressionDao.findByPersonIdAndStoryId(personId, storyId);
            if (progression.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(progression.get());
        };
    }

    @ApiOperation( value = "Met à jour la progression d'une personne sur une histoire avec une page définie" )
    @PostMapping(value="/person/{personId}/story/{storyId}/page/{newPageId}")
    public Callable<ResponseEntity<Progression>> postProgressionAction(@PathVariable int personId, @PathVariable int storyId, @PathVariable int newPageId) {
        return () -> ResponseEntity.ok(progressionService.doProgressionAction(personId, storyId, newPageId));
    }


}