package com.tdevilleduc.urthehero.back.service;

import com.tdevilleduc.urthehero.back.model.Person;
import com.tdevilleduc.urthehero.back.model.PersonDTO;

import java.util.List;
import java.util.Optional;

public interface IPersonService {

    boolean exists(Integer personId);
    boolean notExists(Integer personId);
    Optional<Person> findById(Integer personId);
    List<Person> findAll();
    PersonDTO createOrUpdate(PersonDTO personDto);
    void delete(Integer personId);
}
