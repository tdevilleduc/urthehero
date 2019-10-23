package com.tdevilleduc.urthehero.back.service;

import com.tdevilleduc.urthehero.back.AbstractTest;
import com.tdevilleduc.urthehero.back.BackApplication;
import com.tdevilleduc.urthehero.back.model.Person;
import com.tdevilleduc.urthehero.back.service.impl.PersonService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = BackApplication.class)
public class PersonServiceTest extends AbstractTest {

    @Autowired
    private PersonService personService;


    @Test
    public void test_exists_thenCorrect() {
        Integer personId = 1;
        boolean exists = personService.exists(personId);
        Assertions.assertTrue(exists);
    }

    @Test
    public void test_notExists_thenCorrect() {
        Integer personId = 41;
        boolean notExists = personService.notExists(personId);
        Assertions.assertTrue(notExists);
    }

    @Test
    public void test_findByPageId_thenCorrect() {
        Integer personId = 1;
        Person person = personService.findById(personId);

        Assertions.assertNotNull(person);
        Assertions.assertEquals(Integer.valueOf(1), person.getId());
        Assertions.assertEquals("Thomas Deville-Duc", person.getDisplayName());
        Assertions.assertEquals("thomas@gmail.com", person.getEmail());
        Assertions.assertEquals("tdevilleduc", person.getLogin());
        Assertions.assertEquals("password", person.getPassword());
    }
}
