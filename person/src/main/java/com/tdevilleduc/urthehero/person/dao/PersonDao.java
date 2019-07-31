package com.tdevilleduc.urthehero.person.dao;

import com.tdevilleduc.urthehero.person.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PersonDao extends JpaRepository<Person, Integer> {

    Person findById(int id);
}
