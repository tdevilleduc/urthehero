package com.tdevilleduc.urthehero.back.controller

import com.tdevilleduc.urthehero.back.model.Progression
import com.tdevilleduc.urthehero.back.service.IProgressionService
import com.tdevilleduc.urthehero.back.service.IStoryService
import com.tdevilleduc.urthehero.back.service.IUserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.concurrent.Callable
import javax.servlet.http.HttpServletRequest

@Tag(name = "Progression", description = "Progression Controller")
@RestController
@RequestMapping("/api/progression")
internal class ProgressionController() {

    @Autowired
    private lateinit var progressionService: IProgressionService
    @Autowired
    private lateinit var storyService: IStoryService
    @Autowired
    private lateinit var userService: IUserService

    @GetMapping(value = ["/user/{userId}/all"], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "\${swagger.controller.progression.get-all-by-user-id.value}", description = "\${swagger.controller.progression.get-all-by-user-id.notes}")
    @ResponseBody
    fun getAllByUserId(request: HttpServletRequest,
                         @PathVariable userId: Int): Callable<ResponseEntity<MutableList<Progression>>> = Callable {
        if (userService.notExists(userId))
            ResponseEntity.notFound().build()
        else
            ResponseEntity.ok(progressionService.findByUserId(userId))
    }

    @GetMapping(value = ["/user/{userId}/story/{storyId}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "\${swagger.controller.progression.get-by-user-id-and-story-id.value}", description = "\${swagger.controller.progression.get-by-user-id-and-story-id.notes}")
    @ResponseBody
    fun getOneByUserIdAndStoryId(request: HttpServletRequest,
                                   @PathVariable userId: Int,
                                   @PathVariable storyId: Int): Callable<ResponseEntity<Progression>> = Callable {
        if (userService.notExists(userId)) {
            ResponseEntity.notFound().build<Progression>()
        }
        if (storyService.notExists(storyId)) {
            ResponseEntity.notFound().build<Progression>()
        }
        val progression = progressionService.findByUserIdAndStoryId(userId, storyId)
        if (progression.isEmpty) {
            ResponseEntity.notFound().build<Progression>()
        }
        ResponseEntity.ok(progression.get())
    }

    @Operation(summary = "Met à jour la progression d'un utilisateur sur une histoire avec une page définie")
    @PostMapping(value = ["/user/{userId}/story/{storyId}/page/{newPageId}"])
    @ResponseBody
    fun postProgressionAction(request: HttpServletRequest,
                              @PathVariable userId: Int,
                              @PathVariable storyId: Int,
                              @PathVariable newPageId: Int): Callable<ResponseEntity<Progression>> = Callable {
        ResponseEntity.ok(progressionService.doProgressionAction(userId, storyId, newPageId))
    }

}