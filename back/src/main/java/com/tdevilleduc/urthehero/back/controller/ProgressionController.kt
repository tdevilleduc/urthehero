package com.tdevilleduc.urthehero.back.controller

import com.tdevilleduc.urthehero.back.constant.ApplicationConstants
import com.tdevilleduc.urthehero.back.model.Progression
import com.tdevilleduc.urthehero.back.service.IPersonService
import com.tdevilleduc.urthehero.back.service.IProgressionService
import com.tdevilleduc.urthehero.back.service.IStoryService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.concurrent.Callable
import javax.servlet.http.HttpServletRequest

@Api(value = "Progression", tags = ["Progression Controller"])
@RestController
@RequestMapping("/api/progression")
internal class ProgressionController() {

    val logger: Logger = LoggerFactory.getLogger(ProgressionController::class.java)

    @Autowired
    private lateinit var progressionService: IProgressionService
    @Autowired
    private lateinit var storyService: IStoryService
    @Autowired
    private lateinit var personService: IPersonService

    @GetMapping(value = ["/person/{personId}/all"], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "\${swagger.controller.progression.get-all-by-person-id.value}", notes = "\${swagger.controller.progression.get-all-by-person-id.notes}")
    @ResponseBody
    fun getAllByPersonId(request: HttpServletRequest,
                         @PathVariable personId: Int): Callable<ResponseEntity<MutableList<Progression>>> = Callable {
        if (personService.notExists(personId))
            ResponseEntity.notFound().build<MutableList<Progression>>()
        else
            ResponseEntity.ok(progressionService.findByPersonId(personId))
    }

    @GetMapping(value = ["/person/{personId}/story/{storyId}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "\${swagger.controller.progression.get-by-person-id-and-story-id.value}", notes = "\${swagger.controller.progression.get-by-person-id-and-story-id.notes}")
    @ResponseBody
    fun getOneByPersonIdAndStoryId(request: HttpServletRequest,
                                   @PathVariable personId: Int,
                                   @PathVariable storyId: Int): Callable<ResponseEntity<Progression>> = Callable {
        if (logger.isInfoEnabled) {
            logger.info(ApplicationConstants.CONTROLLER_CALL_LOG, request.requestURI)
        }
        if (personService.notExists(personId)) {
            ResponseEntity.notFound().build<Progression>()
        }
        if (storyService.notExists(storyId)) {
            ResponseEntity.notFound().build<Progression>()
        }
        val progression = progressionService.findByPersonIdAndStoryId(personId, storyId)
        if (progression.isEmpty) {
            ResponseEntity.notFound().build<Progression>()
        }
        ResponseEntity.ok(progression.get())
    }

    @ApiOperation(value = "Met à jour la progression d'une personne sur une histoire avec une page définie")
    @PostMapping(value = ["/person/{personId}/story/{storyId}/page/{newPageId}"])
    @ResponseBody
    fun postProgressionAction(request: HttpServletRequest,
                              @PathVariable personId: Int,
                              @PathVariable storyId: Int,
                              @PathVariable newPageId: Int): Callable<ResponseEntity<Progression>> = Callable {
        if (logger.isInfoEnabled) {
            logger.info(ApplicationConstants.CONTROLLER_CALL_LOG, request.requestURI)
        }
        ResponseEntity.ok(progressionService.doProgressionAction(personId, storyId, newPageId))
    }

}