package com.tdevilleduc.urthehero.back.service

import com.tdevilleduc.urthehero.back.AbstractITTest
import com.tdevilleduc.urthehero.back.BackApplication
import com.tdevilleduc.urthehero.back.dao.EnemyDao
import com.tdevilleduc.urthehero.back.model.Enemy
import com.tdevilleduc.urthehero.back.util.TestUtil
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.*

@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [BackApplication::class])
internal class ITEnemyServiceTest : AbstractITTest() {

    @Autowired
    private lateinit var enemyService: IEnemyService
    @Autowired
    private lateinit var enemyDao: EnemyDao

    @Test
    fun test_delete_thenSuccess() {
        var enemy = TestUtil.createEnemy()
        enemy = enemyDao.save(enemy)
        Assertions.assertNotNull(enemy.id)
        val optionalBefore: Optional<Enemy> = enemyDao.findById(enemy.id)
        Assertions.assertTrue(optionalBefore.isPresent)

        // delete entity
        enemyService.delete(enemy.id)

        val optionalAfter: Optional<Enemy> = enemyDao.findById(enemy.id)
        Assertions.assertTrue(optionalAfter.isEmpty)
    }
}