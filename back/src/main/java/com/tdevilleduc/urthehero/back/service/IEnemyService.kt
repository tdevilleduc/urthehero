package com.tdevilleduc.urthehero.back.service

import com.tdevilleduc.urthehero.back.model.Enemy
import com.tdevilleduc.urthehero.back.model.EnemyDTO


interface IEnemyService {
    fun exists(enemyId: Int?): Boolean
    fun notExists(enemyId: Int?): Boolean
    fun findById(enemyId: Int): Enemy
    fun findByLevel(level: Int): Enemy
    fun createOrUpdate(enemyDto: EnemyDTO): EnemyDTO
    fun delete(enemyId: Int)
}