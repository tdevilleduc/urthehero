package com.tdevilleduc.urthehero.back.controller

import com.tdevilleduc.urthehero.back.constant.ApplicationConstants
import com.tdevilleduc.urthehero.back.exceptions.PersonInternalErrorException
import com.tdevilleduc.urthehero.back.model.Person
import com.tdevilleduc.urthehero.back.model.PersonDTO
import com.tdevilleduc.urthehero.back.service.IPersonService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.slf4j.helpers.MessageFormatter
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.util.Assert
import org.springframework.web.bind.annotation.*
import java.util.concurrent.Callable

@Tag(name = "Person", description = "Person Controller")
@RestController
@RequestMapping("/api/person")
internal class PersonController(private val personService: IPersonService) {
    val logger: Logger = LoggerFactory.getLogger(PersonController::class.java)

    @GetMapping(value = ["/all"], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "\${swagger.controller.person.get-all.value}", description = "\${swagger.controller.person.get-all.notes}")
    @ResponseBody
    fun getPersons(request: ServerHttpRequest): Callable<ResponseEntity<MutableList<Person>>> = Callable {
        if (logger.isInfoEnabled) {
            logger.info(ApplicationConstants.CONTROLLER_CALL_LOG, request.uri.toString())
        }
        ResponseEntity.ok(personService.findAll())
    }

    @GetMapping(value = ["/{personId}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "\${swagger.controller.person.get-by-id.value}", description = "\${swagger.controller.person.get-by-id.notes}")
    @ResponseBody
    fun getPersonById(request: ServerHttpRequest,
                      @PathVariable personId: Int): Callable<ResponseEntity<Person>> = Callable {
        if (logger.isInfoEnabled) {
            logger.info(ApplicationConstants.CONTROLLER_CALL_LOG, request.uri.toString())
        }
        ResponseEntity.ok(personService.findById(personId))
    }

    @PutMapping
    @ResponseBody
    fun createPerson(request: ServerHttpRequest,
                     @RequestBody personDto: PersonDTO): Callable<ResponseEntity<PersonDTO>> = Callable {
        if (logger.isInfoEnabled) {
            logger.info(ApplicationConstants.CONTROLLER_CALL_LOG, request.uri.toString())
        }
        if (personService.exists(personDto.id)) {
            throw PersonInternalErrorException(MessageFormatter.format("Une personne avec l'identifiant {} existe déjà. Elle ne peut être créée", personDto.id).message)
        }
        ResponseEntity.ok(personService.createOrUpdate(personDto))
    }

    @PostMapping
    @ResponseBody
    fun updatePerson(request: ServerHttpRequest,
                     @RequestBody personDto: PersonDTO): Callable<ResponseEntity<PersonDTO>> = Callable {
        if (logger.isInfoEnabled) {
            logger.info(ApplicationConstants.CONTROLLER_CALL_LOG, request.uri.toString())
        }
        Assert.notNull(personDto.id) { throw PersonInternalErrorException("L'identifiant de la personne passée en paramètre ne peut pas être null") }
        ResponseEntity.ok(personService.createOrUpdate(personDto))
    }

    @DeleteMapping(value = ["/{personId}"])
    @ResponseBody
    fun deletePerson(request: ServerHttpRequest,
                     @PathVariable personId: Int) {
        if (logger.isInfoEnabled) {
            logger.info(ApplicationConstants.CONTROLLER_CALL_LOG, request.uri.toString())
        }
        personService.delete(personId)
    }

}