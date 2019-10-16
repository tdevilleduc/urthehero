package com.tdevilleduc.urthehero.back.service;

import com.tdevilleduc.urthehero.back.model.Person;

public interface IPersonService {

    boolean exists(Integer personId);
    boolean notExists(Integer personId);
    Person findById(Integer personId);
}
