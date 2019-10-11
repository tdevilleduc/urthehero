package com.tdevilleduc.urthehero.back.service.impl;

import com.tdevilleduc.urthehero.back.dao.PersonDao;
import com.tdevilleduc.urthehero.back.model.Person;
import com.tdevilleduc.urthehero.back.service.IPersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
