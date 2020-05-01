package com.tdevilleduc.urthehero.back.dao

import com.tdevilleduc.urthehero.back.model.Enemy
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EnemyDao : JpaRepository<Enemy, Int> {
    fun findByLevel(level: Int): List<Enemy>
}