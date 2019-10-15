package com.tdevilleduc.urthehero.back.controller;

import com.tdevilleduc.urthehero.back.dao.StoryDao;
import com.tdevilleduc.urthehero.back.exceptions.StoryNotFoundException;
import com.tdevilleduc.urthehero.back.model.Story;
import com.tdevilleduc.urthehero.back.service.IPersonService;
import com.tdevilleduc.urthehero.back.service.IStoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Api(value = "Story", tags = { "Story Controller" } )
@RestController
@RequestMapping("/Story")
public class StoryController {

    @Autowired
    private StoryDao storyDao;

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

    @PutMapping
    public void createStory(@RequestBody Story story) {
        storyDao.save(story);
    }

    @DeleteMapping(value = "/{storyId}")
    public void deleteStory(@PathVariable Integer storyId) {
        Story story = storyService.findById(storyId);
        storyDao.delete(story);
    }
}
