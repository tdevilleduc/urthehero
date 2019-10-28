package com.tdevilleduc.urthehero.back.service.impl;

import com.tdevilleduc.urthehero.back.dao.PersonDao;
import com.tdevilleduc.urthehero.back.exceptions.PersonNotFoundException;
import com.tdevilleduc.urthehero.back.model.Person;
import com.tdevilleduc.urthehero.back.service.IPersonService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class PersonService implements IPersonService {

    @Autowired
    private PersonDao personDao;

    public boolean exists(Integer personId) {
        Optional<Person> person = personDao.findById(personId);
        if (person.isEmpty()) {
            log.error("La personne avec l'id {} n'existe pas", personId);
            return false;
        }
        return true;
    }

    public boolean notExists(Integer personId) {
        return ! exists(personId);
    }

    @CircuitBreaker(name = "personService_findById", fallbackMethod = "emptyPerson")
    public Optional<Person> findById(Integer personId) {
        Assert.notNull(personId, "The personId parameter is mandatory !");
        return personDao.findById(personId);
    }

    private Optional<Person> emptyPerson(Integer personId, IllegalArgumentException e) {
        return Optional.empty();
    }

    private Optional<Person> emptyPerson(Integer personId, Throwable e) {
        log.error("Unable to retrieve person with id {}", personId, e);
        return Optional.empty();
    }

    @CircuitBreaker(name = "personService_findAll", fallbackMethod = "emptyPersonList")
    public List<Person> findAll() {
        return personDao.findAll();
    }

    private List<Person> emptyPersonList(Throwable e) {
        log.error("Unable to retrieve person list", e);
        return Collections.emptyList();
    }

    public Person createOrUpdate(Person person) {
        return personDao.save(person);
    }

    public void delete(Integer personId) {
        Assert.notNull(personId, "The personId parameter is mandatory !");
        Optional<Person> optional = findById(personId);
        optional
            .ifPresentOrElse(person -> personDao.delete(person),
                () -> {
                    throw new PersonNotFoundException(MessageFormatter.format("La personne avec l'id {} n'existe pas", personId).getMessage());
                }
        );
    }
}
