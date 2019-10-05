package com.tdevilleduc.urthehero.back.controller;

import com.tdevilleduc.urthehero.back.dao.PageDao;
import com.tdevilleduc.urthehero.back.dao.PersonDao;
import com.tdevilleduc.urthehero.back.dao.ProgressionDao;
import com.tdevilleduc.urthehero.back.exceptions.PersonNotFoundException;
import com.tdevilleduc.urthehero.back.model.Person;
import com.tdevilleduc.urthehero.back.model.Progression;
import com.tdevilleduc.urthehero.back.model.Story;
import com.tdevilleduc.urthehero.back.service.IStoryService;
import com.tdevilleduc.urthehero.back.service.impl.StoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Slf4j
@Api(value = "Story", tags = { "Story Controller" } )
@RestController
@RequestMapping("/Story")
public class StoryController {

    @Autowired
    private PageDao pageDao;
    @Autowired
    private PersonDao personDao;
    @Autowired
    private ProgressionDao progressionDao;

    @Autowired
    private IStoryService storyService;

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


    @ApiOperation( value = "Récupère une histoire à partir de son identifiant storyId, avec la progression de la personne personId" )
    @GetMapping(value = "/{storyId}/Person/{personId}")
    public Story getStoryByStoryIdAndPersonId(@PathVariable int storyId, @PathVariable Integer personId) {
        Story story = storyService.findById(storyId);

        Optional<Person> person = personDao.findById(personId);
        if (person.isEmpty()) {
            throw new PersonNotFoundException("L'utilisateur avec l'id "+ personId +" n'existe pas");
        }

        Optional<Progression> progression = progressionDao.findByPersonIdAndStoryId(personId, storyId);
        if (progression.isPresent()) {
            story.setCurrentPageId(progression.get().getActualPageId());
        } else {
            story.setCurrentPageId(story.getFirstPageId());
        }

        return story;
    }

}
