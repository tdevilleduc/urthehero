package com.tdevilleduc.urthehero.back.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testcontainers.shaded.org.apache.commons.lang.RandomStringUtils;

public class PersonTest {

    @Test
    public void test_Constructor() {
        String personLogin = RandomStringUtils.random(20);
        String personDisplayName = RandomStringUtils.random(20);
        String personEmail = RandomStringUtils.random(20);
        Person person = new Person(personLogin, personDisplayName, personEmail);

        Assertions.assertEquals(person.getLogin(), personLogin);
        Assertions.assertEquals(person.getDisplayName(), personDisplayName);
        Assertions.assertEquals(person.getEmail(), personEmail);

        Person secondPerson = new Person();
        secondPerson.setLogin(personLogin);
        secondPerson.setDisplayName(personDisplayName);
        secondPerson.setEmail(personEmail);

        Assertions.assertEquals(secondPerson.toString(), person.toString());
        Assertions.assertEquals(secondPerson, person);
    }
}
