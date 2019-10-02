package com.tdevilleduc.urthehero.back.dao;

import com.tdevilleduc.urthehero.model.Story;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoryDao extends JpaRepository<Story, Integer> {

    Story findById(int id);
}
