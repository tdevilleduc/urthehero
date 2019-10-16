package com.tdevilleduc.urthehero.back.controller;

import com.tdevilleduc.urthehero.back.dao.StoryDao;
import com.tdevilleduc.urthehero.back.exceptions.StoryInternalErrorException;
import com.tdevilleduc.urthehero.back.exceptions.StoryNotFoundException;
import com.tdevilleduc.urthehero.back.model.Story;
import com.tdevilleduc.urthehero.back.service.IPageService;
import com.tdevilleduc.urthehero.back.service.IPersonService;
import com.tdevilleduc.urthehero.back.service.IStoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
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
    @Autowired
    private IPageService pageService;

    @ApiOperation( value = "Récupère la liste des histoires" )
    @GetMapping(value = "/all")
    public List<Story> getAllStories() {
        return storyService.findAll();
    }

    @ApiOperation( value = "Récupère une histoire à partir de son identifiant" )
    @GetMapping(value = "/{storyId}")
    public Story getStoryById(@PathVariable @NotNull Integer storyId) {
        return storyService.findById(storyId);
    }

    @GetMapping(value = "/all/Person/{personId}")
    public List<Story> getStoryByPersonId(@PathVariable @NotNull Integer personId) {
        if (personService.notExists(personId)) {
            throw new StoryNotFoundException(String.format("La personne avec l'id {} n'existe pas", personId));
        }
        return storyService.findByPersonId(personId);
    }

    @PutMapping
    public Story createStory(@RequestBody @NotNull Story story) {
        Assertions.assertNotNull(story.getAuthorId(), () -> {
            throw new StoryInternalErrorException("L'auteur de l'histoire passée en paramètre ne peut pas être null");
        });
        Assertions.assertNotNull(story.getFirstPageId(), () -> {
            throw new StoryInternalErrorException("La première page de l'histoire passée en paramètre ne peut pas être null");
        });
        if (personService.notExists(story.getAuthorId())) {
            throw new StoryInternalErrorException(MessageFormatter.format("La personne avec l'id {} n'existe pas", story.getAuthorId()).getMessage());
        }
        if (pageService.notExists(story.getFirstPageId())) {
            throw new StoryInternalErrorException(MessageFormatter.format("La page avec l'id {} n'existe pas", story.getFirstPageId()).getMessage());
        }
        if (story.getId() != null && storyService.exists(story.getId())) {
            throw new StoryInternalErrorException(MessageFormatter.format("L'id {} existe déjà. Elle ne peut être créée", story.getId()).getMessage());
        }
        return storyService.createOrUpdate(story);
    }

    @PostMapping
    public Story updateStory(@RequestBody @NotNull Story story) {
        Assertions.assertNotNull(story.getAuthorId(), () -> {
            throw new StoryInternalErrorException("L'auteur de l'histoire passée en paramètre ne peut pas être null");
        });
        Assertions.assertNotNull(story.getFirstPageId(), () -> {
            throw new StoryInternalErrorException("La première page de l'histoire passée en paramètre ne peut pas être null");
        });
        Assertions.assertNotNull(story.getId(), () -> {
            throw new StoryInternalErrorException("L'identifiant de l'histoire passée en paramètre ne peut pas être null");
        });
        if (personService.notExists(story.getAuthorId())) {
            throw new StoryInternalErrorException(MessageFormatter.format("La personne avec l'id {} n'existe pas", story.getAuthorId()).getMessage());
        }
        if (pageService.notExists(story.getFirstPageId())) {
            throw new StoryInternalErrorException(MessageFormatter.format("La page avec l'id {} n'existe pas", story.getFirstPageId()).getMessage());
        }
        if (storyService.notExists(story.getId())) {
            throw new StoryInternalErrorException(MessageFormatter.format("L'id {} n'existe pas", story.getId()).getMessage());
        }
        return storyService.createOrUpdate(story);
    }

    @DeleteMapping(value = "/{storyId}")
    public void deleteStory(@PathVariable @NotNull Integer storyId) {
        storyService.delete(storyId);
    }
}
