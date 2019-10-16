package com.tdevilleduc.urthehero.back.controller;

import com.tdevilleduc.urthehero.back.exceptions.PersonNotFoundException;
import com.tdevilleduc.urthehero.back.model.Person;
import com.tdevilleduc.urthehero.back.service.impl.PersonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value = "Person", tags = { "Person Controller" } )
@RestController
@RequestMapping("/Person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @ApiOperation( value = "Récupère la liste des utilisateurs" )
    @GetMapping(value="/all")
    public List<Person> getPersons() {
        return personService.findAll();
    }

    @ApiOperation( value = "Récupère un utilisateur par son identifiant id" )
    @GetMapping(value="/{id}")
    public Person getPersonById(@PathVariable int id) {
        Person person = personService.findById(id);
        if (person == null) {
            throw new PersonNotFoundException("L'utilisateur avec l'id "+id+" n'existe pas");
        }

        return person;
    }
}
