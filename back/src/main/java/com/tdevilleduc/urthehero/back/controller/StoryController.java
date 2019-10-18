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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @ApiOperation( value = "Récupère la liste des histoires" )
    @GetMapping(value = "/all")
    public List<Story> getAllStories() {
        return storyService.findAll();
    }

    @ApiOperation( value = "Récupère une histoire à partir de son identifiant" )
    @GetMapping(value = "/{storyId}")
    public Story getStoryById(@PathVariable int storyId) {
        return storyService.findById(storyId);
    }

    @GetMapping(value = "/all/Person/{personId}")
    public List<Story> getStoryByPersonId(@PathVariable Integer personId) {
        if (personService.notExists(personId)) {
            throw new StoryNotFoundException(String.format("La personne avec l'id {} n'existe pas", personId));
        }
        return storyService.findByPersonId(personId);
    }
}
