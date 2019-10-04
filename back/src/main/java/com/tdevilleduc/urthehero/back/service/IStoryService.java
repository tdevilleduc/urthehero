package com.tdevilleduc.urthehero.back.service;

public interface IStoryService {

    public boolean exists(Integer storyId);
    public boolean notExists(Integer storyId);
}
