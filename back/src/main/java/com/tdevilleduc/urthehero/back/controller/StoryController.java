package com.tdevilleduc.urthehero.back.controller;

import com.tdevilleduc.urthehero.back.exceptions.StoryNotFoundException;
import com.tdevilleduc.urthehero.back.model.Story;
import com.tdevilleduc.urthehero.back.service.IPersonService;
import com.tdevilleduc.urthehero.back.service.IStoryService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;

@Slf4j
@Api(value = "Story", tags = { "Story Controller" } )
@RestController
@RequestMapping("/Story")
@CircuitBreaker(name = "storyController")
public class StoryController {

    @Autowired
    private IStoryService storyService;
    @Autowired
    private IPersonService personService;

    @ResponseBody
    @ApiOperation( value = "Récupère la liste des histoires" )
    @GetMapping(value = "/all")
    public Callable<ResponseEntity<List<Story>>> getAllStories() {
        return () -> ResponseEntity.ok(storyService.findAll());
    }

    @ResponseBody
    @ApiOperation( value = "Récupère une histoire à partir de son identifiant" )
    @GetMapping(value = "/{storyId}")
    public Callable<ResponseEntity<Story>> getStoryById(@PathVariable int storyId) {
        return () -> {
            Optional<Story> optional = this.storyService.findById(storyId);
            return optional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        };
    }

    @ResponseBody
    @GetMapping(value = "/all/Person/{personId}")
    public Callable<ResponseEntity<List<Story>>> getStoryByPersonId(@PathVariable Integer personId) {
        if (personService.notExists(personId)) {
            throw new StoryNotFoundException(String.format("La personne avec l'id {} n'existe pas", personId));
        }
        return () -> ResponseEntity.ok(storyService.findByPersonId(personId));
    }
}
