package com.tdevilleduc.urthehero.back.service;

public interface IPersonService {

    boolean exists(Integer personId);
    boolean notExists(Integer personId);

}
