package com.tdevilleduc.urthehero.back.dao;

import com.tdevilleduc.urthehero.back.model.Progression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProgressionDao extends JpaRepository<Progression, Integer> {

    Optional<Progression> findByPersonIdAndStoryId(Integer personId, Integer storyId);
    List<Progression> findByPersonId(Integer personId);

    List<Progression> findByStoryId(Integer storyId);

    Long countByStoryId(Integer storyId);


}
