package com.tdevilleduc.urthehero.back.controller;

import com.tdevilleduc.urthehero.back.dao.PersonDao;
import com.tdevilleduc.urthehero.back.exceptions.PersonNotFoundException;
import com.tdevilleduc.urthehero.back.model.Person;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api( value = "API users pour les interactions avec les utilisateurs" )
@RestController
@RequestMapping("/Person")
public class PersonController {

    @Autowired
    private PersonDao personDao;

    @ApiOperation( value = "Récupère la liste des utilisateurs" )
    @GetMapping(value="/all")
    public List<Person> getPersons() {
        return personDao.findAll();
    }

    @ApiOperation( value = "Récupère un utilisateur par son identifiant id" )
    @GetMapping(value="/{id}")
    public Person getPersonById(@PathVariable int id) {
        Person person = personDao.findById(id);

        if (person == null) {
            throw new PersonNotFoundException("L'utilisateur avec l'id "+id+" n'existe pas");
        }

        return person;
    }
}
