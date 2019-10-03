package com.tdevilleduc.urthehero.back.dao;

import com.tdevilleduc.urthehero.back.model.Progression;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProgressionDao extends JpaRepository<Progression, Integer> {

    Progression findById(int id);
    Progression findByPersonIdAndStoryId(Integer personId, Integer storyId);
    List<Progression> findByPersonId(Integer personId);

}
