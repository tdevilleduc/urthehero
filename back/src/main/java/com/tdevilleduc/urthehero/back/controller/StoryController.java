package com.tdevilleduc.urthehero.back.controller;

import com.tdevilleduc.urthehero.back.dao.PageDao;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@Api( value = "API Story pour les interactions avec les histoires" )
@RestController
@RequestMapping("/Story")
public class StoryController {

    @Autowired
    private PageDao pageDao;
    @Autowired
    private StoryDao storyDao;
    @Autowired
    private PersonDao personDao;
    @Autowired
    private ProgressionDao progressionDao;

    @ApiOperation( value = "Récupère la liste des histoires" )
    @GetMapping(value = "/all")
    public List<Story> getAllStories() {
        return storyDao.findAll();
    }

    @ApiOperation( value = "Récupère une histoire à partir de son identifiant" )
    @GetMapping(value = "/{storyId}")
    public Story getStoryById(@PathVariable int storyId) {
        Optional<Story> story = storyDao.findById(storyId);
        if (story.isEmpty()) {
            throw new StoryNotFoundException("L'histoire avec l'id " + storyId + " n'existe pas");
        }

        return story.get();
    }


    @ApiOperation( value = "Récupère une histoire à partir de son identifiant storyId, avec la progression de la personne personId" )
    @GetMapping(value = "/{storyId}/Person/{personId}")
    public Story getStoryByStoryIdAndPersonId(@PathVariable int storyId, @PathVariable Integer personId) {
        Optional<Story> optionalStory = storyDao.findById(storyId);
        if (optionalStory.isEmpty()) {
            throw new StoryNotFoundException("L'histoire avec l'id " + storyId + " n'existe pas");
        }

        Story story = optionalStory.get();

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
