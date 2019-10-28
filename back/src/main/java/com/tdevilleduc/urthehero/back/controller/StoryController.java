package com.tdevilleduc.urthehero.back.controller;

import com.tdevilleduc.urthehero.back.exceptions.StoryInternalErrorException;
import com.tdevilleduc.urthehero.back.model.Story;
import com.tdevilleduc.urthehero.back.service.IPageService;
import com.tdevilleduc.urthehero.back.service.IPersonService;
import com.tdevilleduc.urthehero.back.service.IStoryService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;

@Slf4j
@Api(value = "Story", tags = { "Story Controller" } )
@RestController
@RequestMapping("/api/story")
@CircuitBreaker(name = "storyController")
@Retry(name = "storyController")
public class StoryController {

    private final IStoryService storyService;
    private final IPersonService personService;
    private final IPageService pageService;

    public StoryController(IStoryService storyService, IPersonService personService, IPageService pageService) {
        this.storyService = storyService;
        this.personService = personService;
        this.pageService = pageService;
    }


    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(
            value = "${swagger.controller.story.get-all.value}",
            notes = "${swagger.controller.story.get-all.notes}")
    public @ResponseBody Callable<ResponseEntity<List<Story>>> getAllStories() {
        return () -> ResponseEntity.ok(storyService.findAll());
    }

    @GetMapping(value = "/{storyId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(
            value = "${swagger.controller.story.get-by-id.value}",
            notes = "${swagger.controller.story.get-by-id.notes}")
    public @ResponseBody Callable<ResponseEntity<Story>> getStoryById(
            @PathVariable Integer storyId) {
        return () -> {
            Optional<Story> optional = this.storyService.findById(storyId);
            return optional
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        };
    }

    @GetMapping(value = "/all/person/{personId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(
            value = "${swagger.controller.story.get-by-person-id.value}",
            notes = "${swagger.controller.story.get-by-person-id.notes}")
    public @ResponseBody Callable<ResponseEntity<List<Story>>> getStoryByPersonId(
            @PathVariable Integer personId) {
        return () -> {
            if (personService.notExists(personId)) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(this.storyService.findByPersonId(personId));
        };
    }

    @PutMapping
    public @ResponseBody Story createStory(@RequestBody Story story) {
        //TODO: déplacer les controles dans le service ?
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
    public @ResponseBody Story updateStory(@RequestBody Story story) {
        //TODO: déplacer les controles dans le service ?
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
    public @ResponseBody void deleteStory(@PathVariable Integer storyId) {
        storyService.delete(storyId);
    }
}
