package com.tdevilleduc.urthehero.back.controller;

import com.tdevilleduc.urthehero.back.model.Progression;
import com.tdevilleduc.urthehero.back.service.IPersonService;
import com.tdevilleduc.urthehero.back.service.IProgressionService;
import com.tdevilleduc.urthehero.back.service.IStoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;

@Api(value = "Progression", tags = { "Progression Controller" } )
@RestController
@RequestMapping("/api/progression")
public class ProgressionController {

    private IProgressionService progressionService;
    private IStoryService storyService;
    private IPersonService personService;

    public ProgressionController(IProgressionService progressionService, IStoryService storyService, IPersonService personService) {
        this.progressionService = progressionService;
        this.storyService = storyService;
        this.personService = personService;
    }

    @ApiOperation( value = "Récupère la liste des histoires en cours d'une personne" )
    @GetMapping(value="/person/{personId}/all")
    public Callable<ResponseEntity<List<Progression>>> getAllByPersonId(@PathVariable Integer personId) {
        return () -> {
            if (personService.notExists(personId)) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(progressionService.findByPersonId(personId));
        };
    }

    @ApiOperation( value = "Récupère la progression d'une personne sur une histoire" )
    @GetMapping(value="/person/{personId}/story/{storyId}")
    public Callable<ResponseEntity<Progression>> getOneByPersonIdAndStoryId(@PathVariable Integer personId, @PathVariable Integer storyId) {
        return () -> {
            if (personService.notExists(personId)) {
                return ResponseEntity.notFound().build();
            }

            if (storyService.notExists(storyId)) {
                return ResponseEntity.notFound().build();
            }

            Optional<Progression> progression = progressionService.findByPersonIdAndStoryId(personId, storyId);
            if (progression.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(progression.get());
        };
    }

    @ApiOperation( value = "Met à jour la progression d'une personne sur une histoire avec une page définie" )
    @PostMapping(value="/person/{personId}/story/{storyId}/page/{newPageId}")
    public Callable<ResponseEntity<Progression>> postProgressionAction(@PathVariable Integer personId, @PathVariable Integer storyId, @PathVariable Integer newPageId) {
        return () -> ResponseEntity.ok(progressionService.doProgressionAction(personId, storyId, newPageId));
    }


}