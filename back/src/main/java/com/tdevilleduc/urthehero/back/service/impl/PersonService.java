package com.tdevilleduc.urthehero.back.service.impl;

import com.tdevilleduc.urthehero.back.convertor.PersonConvertor;
import com.tdevilleduc.urthehero.back.dao.PersonDao;
import com.tdevilleduc.urthehero.back.exceptions.PersonNotFoundException;
import com.tdevilleduc.urthehero.back.model.Page;
import com.tdevilleduc.urthehero.back.model.Person;
import com.tdevilleduc.urthehero.back.model.PersonDTO;
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

import static com.tdevilleduc.urthehero.back.config.ResilienceConstants.INSTANCE_PERSON_SERVICE;

@Slf4j
@Service
public class PersonService implements IPersonService {

    @Autowired
    private PersonDao personDao;

    @Autowired
    private PersonConvertor personConvertor;

    @CircuitBreaker(name = INSTANCE_PERSON_SERVICE, fallbackMethod = "notExists")
    public boolean exists(final Integer personId) {
        Optional<Person> person = personDao.findById(personId);
        if (person.isEmpty()) {
            log.error("La personne avec l'id {} n'existe pas", personId);
            return false;
        }
        return true;
    }

    @CircuitBreaker(name = INSTANCE_PERSON_SERVICE, fallbackMethod = "notExists")
    public boolean notExists(final Integer personId) {
        return ! exists(personId);
    }

    @SuppressWarnings({"squid:UnusedPrivateMethod", "squid:S1172"})
    private boolean notExists(final Integer personId, final Throwable e) {
        return false;
    }

    @CircuitBreaker(name = INSTANCE_PERSON_SERVICE, fallbackMethod = "emptyPerson")
    public Optional<Person> findById(final Integer personId) {
        Assert.notNull(personId, "The personId parameter is mandatory !");
        return personDao.findById(personId);
    }

    @SuppressWarnings({"squid:UnusedPrivateMethod", "squid:S1172"})
    private Optional<Person> emptyPerson(final Integer personId, final Throwable e) {
        log.error("Unable to retrieve person with id {}", personId, e);
        return Optional.empty();
    }

    @CircuitBreaker(name = INSTANCE_PERSON_SERVICE, fallbackMethod = "emptyPersonList")
    public List<Person> findAll() {
        return personDao.findAll();
    }

    @SuppressWarnings({"squid:UnusedPrivateMethod", "squid:S1172"})
    private List<Person> emptyPersonList(final Throwable e) {
        log.error("Unable to retrieve person list", e);
        return Collections.emptyList();
    }

    public PersonDTO createOrUpdate(PersonDTO personDto) {
        Person person = personConvertor.convertToEntity(personDto);
        return personConvertor.convertToDto(personDao.save(person));
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
