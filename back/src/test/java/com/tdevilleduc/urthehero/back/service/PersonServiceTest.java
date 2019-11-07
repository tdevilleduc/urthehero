package com.tdevilleduc.urthehero.back.service;

import com.tdevilleduc.urthehero.back.AbstractTest;
import com.tdevilleduc.urthehero.back.BackApplication;
import com.tdevilleduc.urthehero.back.exceptions.PersonNotFoundException;
import com.tdevilleduc.urthehero.back.model.Person;
import com.tdevilleduc.urthehero.back.service.impl.PersonService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = BackApplication.class)
class PersonServiceTest extends AbstractTest {

    @Autowired
    private PersonService personService;

    @Test
    void test_exists_thenCorrect() {
        Integer personId = 1;
        boolean exists = personService.exists(personId);
        Assertions.assertTrue(exists);
    }

    @Test
    void test_notExists_thenCorrect() {
        Integer personId = 41;
        boolean notExists = personService.notExists(personId);
        Assertions.assertTrue(notExists);
    }

    @Test
    void test_findById_thenCorrect() {
        Integer personId = 1;
        Optional<Person> optional = personService.findById(personId);
        Assertions.assertTrue(optional.isPresent());
        Person person = optional.get();

        Assertions.assertNotNull(person);
        Assertions.assertEquals(Integer.valueOf(1), person.getId());
        Assertions.assertEquals("Thomas Deville-Duc", person.getDisplayName());
        Assertions.assertEquals("thomas@gmail.com", person.getEmail());
        Assertions.assertEquals("tdevilleduc", person.getLogin());
        Assertions.assertEquals("password", person.getPassword());
    }

    @Test
    void test_findById_thenNotFound() {
        Integer personId = 13;
        Optional<Person> optional = personService.findById(personId);
        Assertions.assertTrue(optional.isEmpty());
    }

    @Test
    void findByIdWithIdNull() {
        // with resilience4j, IllegalArgumentException is catched and fallback method 'emptyPerson' is called
//        Assertions.assertThrows(IllegalArgumentException.class, () -> personService.findById(null));
        assertEquals(Optional.empty(), personService.findById(null));
    }

    @Test
    void deleteWithIdNull() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> personService.delete(null));
    }

    @Test
    void delete_thenNotFound() {
        Integer personId = 13;
        Assertions.assertThrows(PersonNotFoundException.class, () -> personService.delete(personId));
    }
}
