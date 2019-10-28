package com.tdevilleduc.urthehero.back.controller;

import com.tdevilleduc.urthehero.back.model.Progression;
import com.tdevilleduc.urthehero.back.service.IPersonService;
import com.tdevilleduc.urthehero.back.service.IProgressionService;
import com.tdevilleduc.urthehero.back.service.IStoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;

@Slf4j
@Api(value = "Progression", tags = { "Progression Controller" } )
@RestController
@RequestMapping("/api/progression")
public class ProgressionController {

    private final IProgressionService progressionService;
    private final IStoryService storyService;
    private final IPersonService personService;

    public ProgressionController(IProgressionService progressionService, IStoryService storyService, IPersonService personService) {
        this.progressionService = progressionService;
        this.storyService = storyService;
        this.personService = personService;
    }

    @GetMapping(value="/person/{personId}/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(
            value = "${swagger.controller.progression.get-all-by-person-id.value}",
            notes = "${swagger.controller.progression.get-all-by-person-id.notes}")
    public @ResponseBody Callable<ResponseEntity<List<Progression>>> getAllByPersonId(HttpServletRequest request,
                                                                                      @PathVariable Integer personId) {
        return () -> {
            if (log.isInfoEnabled()) {
                log.info("call: {}", request.getRequestURI());
            }
            if (personService.notExists(personId)) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(progressionService.findByPersonId(personId));
        };
    }

    @GetMapping(value="/person/{personId}/story/{storyId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(
            value = "${swagger.controller.progression.get-by-person-id-and-story-id.value}",
            notes = "${swagger.controller.progression.get-by-person-id-and-story-id.notes}")
    public @ResponseBody Callable<ResponseEntity<Progression>> getOneByPersonIdAndStoryId(HttpServletRequest request,
                                                                                          @PathVariable Integer personId,
                                                                                          @PathVariable Integer storyId) {
        return () -> {
            if (log.isInfoEnabled()) {
                log.info("call: {}", request.getRequestURI());
            }
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
    public @ResponseBody Callable<ResponseEntity<Progression>> postProgressionAction(HttpServletRequest request,
                                                                       @PathVariable Integer personId,
                                                                       @PathVariable Integer storyId,
                                                                       @PathVariable Integer newPageId) {
        return () -> {
            if (log.isInfoEnabled()) {
                log.info("call: {}", request.getRequestURI());
            }
            return ResponseEntity.ok(progressionService.doProgressionAction(personId, storyId, newPageId));
        };
    }


}