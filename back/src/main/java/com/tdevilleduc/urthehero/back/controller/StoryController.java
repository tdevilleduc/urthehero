package com.tdevilleduc.urthehero.back.controller;

import com.tdevilleduc.urthehero.back.exceptions.StoryInternalErrorException;
import com.tdevilleduc.urthehero.back.model.Story;
import com.tdevilleduc.urthehero.back.model.StoryDTO;
import com.tdevilleduc.urthehero.back.service.IPageService;
import com.tdevilleduc.urthehero.back.service.IPersonService;
import com.tdevilleduc.urthehero.back.service.IStoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;

import static com.tdevilleduc.urthehero.back.constant.ApplicationConstants.*;

@Slf4j
@Api(value = "Story", tags = { "Story Controller" } )
@RestController
@RequestMapping("/api/story")
class StoryController {

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
    public @ResponseBody Callable<ResponseEntity<List<Story>>> getAllStories(HttpServletRequest request) {
        return () -> {
            if (log.isInfoEnabled()) {
                log.info(CONTROLLER_CALL_LOG, request.getRequestURI());
            }
            return ResponseEntity.ok(storyService.findAll());
        };
    }

    @GetMapping(value = "/{storyId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(
            value = "${swagger.controller.story.get-by-id.value}",
            notes = "${swagger.controller.story.get-by-id.notes}")
    public @ResponseBody Callable<ResponseEntity<Story>> getStoryById(HttpServletRequest request,
                                                                      @PathVariable Integer storyId) {
        return () -> {
            if (log.isInfoEnabled()) {
                log.info(CONTROLLER_CALL_LOG, request.getRequestURI());
            }
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
    public @ResponseBody Callable<ResponseEntity<List<Story>>> getStoryByPersonId(HttpServletRequest request,
                                                                                  @PathVariable Integer personId) {
        return () -> {
            if (log.isInfoEnabled()) {
                log.info(CONTROLLER_CALL_LOG, request.getRequestURI());
            }
            if (personService.notExists(personId)) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(this.storyService.findByPersonId(personId));
        };
    }

    @PutMapping
    public @ResponseBody Callable<ResponseEntity<StoryDTO>> createStory(HttpServletRequest request,
                                           @RequestBody StoryDTO storyDTO) {
        return () -> {
            if (log.isInfoEnabled()) {
                log.info(CONTROLLER_CALL_LOG, request.getRequestURI());
            }
            //TODO: déplacer les controles dans le service ?
            Assert.notNull(storyDTO.getAuthorId(), () -> {
                throw new StoryInternalErrorException("L'auteur de l'histoire passée en paramètre ne peut pas être null");
            });
            Assert.notNull(storyDTO.getFirstPageId(), () -> {
                throw new StoryInternalErrorException("La première page de l'histoire passée en paramètre ne peut pas être null");
            });
            if (personService.notExists(storyDTO.getAuthorId())) {
                throw new StoryInternalErrorException(MessageFormatter.format(ERROR_MESSAGE_PERSON_DOESNOT_EXIST, storyDTO.getAuthorId()).getMessage());
            }
            if (pageService.notExists(storyDTO.getFirstPageId())) {
                throw new StoryInternalErrorException(MessageFormatter.format(ERROR_MESSAGE_PAGE_DOESNOT_EXIST, storyDTO.getFirstPageId()).getMessage());
            }
            if (storyDTO.getStoryId() != null && storyService.exists(storyDTO.getStoryId())) {
                throw new StoryInternalErrorException(MessageFormatter.format("L'id {} existe déjà. Elle ne peut être créée", storyDTO.getStoryId()).getMessage());
            }
            return ResponseEntity.ok(storyService.createOrUpdate(storyDTO));
        };
    }

    @PostMapping
    public @ResponseBody Callable<ResponseEntity<StoryDTO>> updateStory(HttpServletRequest request,
                                                                        @RequestBody StoryDTO storyDto) {
        return () -> {
            if (log.isInfoEnabled()) {
                log.info(CONTROLLER_CALL_LOG, request.getRequestURI());
            }
            //TODO: déplacer les controles dans le service ?
            Assert.notNull(storyDto.getAuthorId(), () -> {
                throw new StoryInternalErrorException("L'auteur de l'histoire passée en paramètre ne peut pas être null");
            });
            Assert.notNull(storyDto.getFirstPageId(), () -> {
                throw new StoryInternalErrorException("La première page de l'histoire passée en paramètre ne peut pas être null");
            });
            Assert.notNull(storyDto.getStoryId(), () -> {
                throw new StoryInternalErrorException("L'identifiant de l'histoire passée en paramètre ne peut pas être null");
            });
            if (personService.notExists(storyDto.getAuthorId())) {
                throw new StoryInternalErrorException(MessageFormatter.format(ERROR_MESSAGE_PERSON_DOESNOT_EXIST, storyDto.getAuthorId()).getMessage());
            }
            if (pageService.notExists(storyDto.getFirstPageId())) {
                throw new StoryInternalErrorException(MessageFormatter.format(ERROR_MESSAGE_PAGE_DOESNOT_EXIST, storyDto.getFirstPageId()).getMessage());
            }
            if (storyService.notExists(storyDto.getStoryId())) {
                throw new StoryInternalErrorException(MessageFormatter.format(ERROR_MESSAGE_STORY_DOESNOT_EXIST, storyDto.getStoryId()).getMessage());
            }
            return ResponseEntity.ok(storyService.createOrUpdate(storyDto));
        };
    }

    @DeleteMapping(value = "/{storyId}")
    public @ResponseBody void deleteStory(HttpServletRequest request,
                                          @PathVariable Integer storyId) {
        if (log.isInfoEnabled()) {
            log.info(CONTROLLER_CALL_LOG, request.getRequestURI());
        }
        storyService.delete(storyId);
    }
}
