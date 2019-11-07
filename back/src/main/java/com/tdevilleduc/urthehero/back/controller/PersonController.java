package com.tdevilleduc.urthehero.back.controller;

import com.tdevilleduc.urthehero.back.exceptions.PersonInternalErrorException;
import com.tdevilleduc.urthehero.back.model.Person;
import com.tdevilleduc.urthehero.back.service.IPersonService;
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
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;

@Slf4j
@Api(value = "Person", tags = { "Person Controller" } )
@RestController
@RequestMapping("/api/person")
class PersonController {

    private final IPersonService personService;

    private PersonController(IPersonService personService) {
        this.personService = personService;
    }

    @GetMapping(value="/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(
            value = "${swagger.controller.person.get-all.value}",
            notes = "${swagger.controller.person.get-all.notes}")
    public @ResponseBody Callable<ResponseEntity<List<Person>>> getPersons(HttpServletRequest request) {
        return () -> {
            if (log.isInfoEnabled()) {
                log.info("call: {}", request.getRequestURI());
            }
            return ResponseEntity.ok(personService.findAll());
        };
    }

    @GetMapping(value="/{personId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(
            value = "${swagger.controller.person.get-by-id.value}",
            notes = "${swagger.controller.person.get-by-id.notes}")
    public @ResponseBody Callable<ResponseEntity<Person>> getPersonById(HttpServletRequest request,
                                                                        @PathVariable Integer personId) {
        return () -> {
            if (log.isInfoEnabled()) {
                log.info("call: {}", request.getRequestURI());
            }
            Optional<Person> optional = personService.findById(personId);
            return optional
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        };
    }
  
    @PutMapping
    public @ResponseBody Callable<ResponseEntity<Person>> createPerson(HttpServletRequest request,
                                                         @RequestBody @NotNull Person person) {
        return () -> {
            if (log.isInfoEnabled()) {
                log.info("call: {}", request.getRequestURI());
            }
            if (person.getId() != null && personService.exists(person.getId())) {
                throw new PersonInternalErrorException(MessageFormatter.format("Une personne avec l'identifiant {} existe déjà. Elle ne peut être créée", person.getId()).getMessage());
            }
            return ResponseEntity.ok(personService.createOrUpdate(person));
        };
    }

    @PostMapping
    public @ResponseBody Callable<ResponseEntity<Person>> updatePerson(HttpServletRequest request,
                               @RequestBody @NotNull Person person) {
        return () -> {
            if (log.isInfoEnabled()) {
                log.info("call: {}", request.getRequestURI());
            }
            Assert.notNull(person.getId(), () -> {
                throw new PersonInternalErrorException("L'identifiant de la personne passée en paramètre ne peut pas être null");
            });
            return ResponseEntity.ok(personService.createOrUpdate(person));
        };
    }

    @DeleteMapping(value = "/{personId}")
    public @ResponseBody void deletePerson(HttpServletRequest request,
                             @PathVariable @NotNull Integer personId) {
        if (log.isInfoEnabled()) {
            log.info("call: {}", request.getRequestURI());
        }
        personService.delete(personId);
    }
}
