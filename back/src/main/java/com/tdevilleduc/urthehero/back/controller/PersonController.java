package com.tdevilleduc.urthehero.back.controller;

import com.tdevilleduc.urthehero.back.exceptions.PersonInternalErrorException;
import com.tdevilleduc.urthehero.back.model.Person;
import com.tdevilleduc.urthehero.back.service.IPersonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.junit.jupiter.api.Assertions;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Api(value = "Person", tags = { "Person Controller" } )
@RestController
@RequestMapping("/Person")
public class PersonController {

    @Autowired
    private IPersonService personService;

    @ApiOperation( value = "Récupère la liste des utilisateurs" )
    @GetMapping(value="/all")
    public List<Person> getPersons() {
        return personService.findAll();
    }

    @ApiOperation( value = "Récupère un utilisateur par son identifiant id" )
    @GetMapping(value="/{id}")
    public Person getPersonById(@PathVariable @NotNull int id) {
        return personService.findById(id);
    }

    @PutMapping
    public Person createStory(@RequestBody @NotNull Person person) {
        if (person.getId() != null && personService.exists(person.getId())) {
            throw new PersonInternalErrorException(MessageFormatter.format("Une personne avec l'identifiant {} existe déjà. Elle ne peut être créée", person.getId()).getMessage());
        }
        return personService.createOrUpdate(person);
    }

    @PostMapping
    public Person updateStory(@RequestBody @NotNull Person story) {
        Assertions.assertNotNull(story.getId(), () -> {
            throw new PersonInternalErrorException("L'identifiant de la personne passée en paramètre ne peut pas être null");
        });
        return personService.createOrUpdate(story);
    }

    @DeleteMapping(value = "/{personId}")
    public void deletePerson(@PathVariable @NotNull Integer personId) {
        personService.delete(personId);
    }
}
