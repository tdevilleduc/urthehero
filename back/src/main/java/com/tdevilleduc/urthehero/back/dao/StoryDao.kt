package com.tdevilleduc.urthehero.back.dao

import com.tdevilleduc.urthehero.back.model.Story
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StoryDao : JpaRepository<Story, Int>