package com.tdevilleduc.urthehero.back.controller;

import com.tdevilleduc.urthehero.back.exceptions.StoryInternalErrorException;
import com.tdevilleduc.urthehero.back.model.Story;
import com.tdevilleduc.urthehero.back.service.IPageService;
import com.tdevilleduc.urthehero.back.service.IPersonService;
import com.tdevilleduc.urthehero.back.service.IStoryService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;

@Slf4j
@Api(value = "Story", tags = { "Story Controller" } )
@RestController
@RequestMapping("/api/story")
@CircuitBreaker(name = "storyController")
public class StoryController {

    @Autowired
    private IStoryService storyService;
    @Autowired
    private IPersonService personService;
    @Autowired
    private IPageService pageService;

    @ResponseBody
    @ApiOperation( value = "Récupère la liste des histoires" )
    @GetMapping(value = "/all")
    public Callable<ResponseEntity<List<Story>>> getAllStories() {
        return () -> ResponseEntity.ok(storyService.findAll());
    }

    @ResponseBody
    @ApiOperation( value = "Récupère une histoire à partir de son identifiant" )
    @GetMapping(value = "/{storyId}")
    public Callable<ResponseEntity<Story>> getStoryById(@PathVariable @NotNull int storyId) {
        return () -> {
            Optional<Story> optional = this.storyService.findById(storyId);
            return optional
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        };
    }

    @ResponseBody
    @GetMapping(value = "/all/person/{personId}")
    public Callable<ResponseEntity<List<Story>>> getStoryByPersonId(@PathVariable @NotNull Integer personId) {
        return () -> {
            if (personService.notExists(personId)) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(this.storyService.findByPersonId(personId));
        };
    }

    @PutMapping
    public Story createStory(@RequestBody @NotNull Story story) {
        Assert.notNull(story.getAuthorId(), () -> {
            throw new StoryInternalErrorException("L'auteur de l'histoire passée en paramètre ne peut pas être null");
        });
        Assert.notNull(story.getFirstPageId(), () -> {
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
        Assert.notNull(story.getAuthorId(), () -> {
            throw new StoryInternalErrorException("L'auteur de l'histoire passée en paramètre ne peut pas être null");
        });
        Assert.notNull(story.getFirstPageId(), () -> {
            throw new StoryInternalErrorException("La première page de l'histoire passée en paramètre ne peut pas être null");
        });
        Assert.notNull(story.getId(), () -> {
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
