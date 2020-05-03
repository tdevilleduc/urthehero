package com.tdevilleduc.urthehero.back.service.impl

import com.tdevilleduc.urthehero.back.config.Mapper
import com.tdevilleduc.urthehero.back.constant.ApplicationConstants
import com.tdevilleduc.urthehero.back.dao.EnemyDao
import com.tdevilleduc.urthehero.back.exceptions.EnemyNotFoundException
import com.tdevilleduc.urthehero.back.model.Enemy
import com.tdevilleduc.urthehero.back.model.EnemyDTO
import com.tdevilleduc.urthehero.back.service.IEnemyService
import lombok.extern.slf4j.Slf4j
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.slf4j.helpers.MessageFormatter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.util.Assert

@Slf4j
@Service
class EnemyService : IEnemyService {
    val logger: Logger = LoggerFactory.getLogger(PageService::class.java)

    @Autowired
    private lateinit var enemyDao: EnemyDao

    override fun exists(enemyId: Int?): Boolean {
        if (enemyId == null)
            return false
        Assert.notNull(enemyId, ApplicationConstants.CHECK_ENEMYID_PARAMETER_MANDATORY)
        val optional = enemyDao.findById(enemyId)
        if (optional.isEmpty) {
            logger.error(ApplicationConstants.ERROR_MESSAGE_ENEMY_DOESNOT_EXIST, enemyId)
            return false
        }
        return true
    }

    override fun notExists(enemyId: Int?): Boolean {
        return !exists(enemyId)
    }

    override fun findById(enemyId: Int): Enemy {
        Assert.notNull(enemyId, ApplicationConstants.CHECK_ENEMYID_PARAMETER_MANDATORY)
        val optional = enemyDao.findById(enemyId)
        if (optional.isPresent) {
            return optional.get()
        } else {
            throw EnemyNotFoundException(MessageFormatter.format(ApplicationConstants.ERROR_MESSAGE_ENEMY_ID_DOESNOT_EXIST, enemyId).message)
        }
    }

    override fun findByLevel(level: Int): Enemy {
       logger.info("level {}", level)
        val enemyByLevel: List<Enemy> = if (level > 0) {
            enemyDao.findByLevel(level)
        } else {
            enemyDao.findAll()
        }

        if (enemyByLevel.isEmpty()) {
            throw EnemyNotFoundException(ApplicationConstants.ERROR_MESSAGE_ENEMY_DOESNOT_EXIST)
        } else {
            val randomId = DiceService.generatingRandomIntegerBounded(enemyByLevel.size)
            return enemyByLevel[randomId-1]
        }
    }

    override fun createOrUpdate(enemyDto: EnemyDTO): EnemyDTO {
        val enemy: Enemy = Mapper.convert(enemyDto)
        return Mapper.convert(enemyDao.save(enemy))
    }

    override fun delete(enemyId: Int) {
        Assert.notNull(enemyId, ApplicationConstants.CHECK_ENEMYID_PARAMETER_MANDATORY)
        val page = findById(enemyId)
        enemyDao.delete(page)
    }
}