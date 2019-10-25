package com.tdevilleduc.urthehero.back.controller;

import com.tdevilleduc.urthehero.back.exceptions.PersonInternalErrorException;
import com.tdevilleduc.urthehero.back.model.Person;
import com.tdevilleduc.urthehero.back.service.IPersonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;

@Api(value = "Person", tags = { "Person Controller" } )
@RestController
@RequestMapping("/api/person")
public class PersonController {

    private final IPersonService personService;

    public PersonController(IPersonService personService) {
        this.personService = personService;
    }

    @ApiOperation( value = "Récupère la liste des utilisateurs" )
    @GetMapping(value="/all")
    public Callable<ResponseEntity<List<Person>>> getPersons() {
        return () -> ResponseEntity.ok(personService.findAll());
    }

    @ApiOperation( value = "Récupère un utilisateur par son identifiant id" )
    @GetMapping(value="/{personId}")
    public Callable<ResponseEntity<Person>> getPersonById(@PathVariable Integer personId) {
        return () -> {
            Optional<Person> optional = personService.findById(personId);
            return optional
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        };
    }
  
    @PutMapping
    public Callable<ResponseEntity<Person>> createPerson(@RequestBody @NotNull Person person) {
        return () -> {
            if (person.getId() != null && personService.exists(person.getId())) {
                throw new PersonInternalErrorException(MessageFormatter.format("Une personne avec l'identifiant {} existe déjà. Elle ne peut être créée", person.getId()).getMessage());
            }
            return ResponseEntity.ok(personService.createOrUpdate(person));
        };
    }

    @PostMapping
    public Person updatePerson(@RequestBody @NotNull Person person) {
        Assert.notNull(person.getId(), () -> {
            throw new PersonInternalErrorException("L'identifiant de la personne passée en paramètre ne peut pas être null");
        });
        return personService.createOrUpdate(person);
    }

    @DeleteMapping(value = "/{personId}")
    public void deletePerson(@PathVariable @NotNull Integer personId) {
        personService.delete(personId);
    }
}
