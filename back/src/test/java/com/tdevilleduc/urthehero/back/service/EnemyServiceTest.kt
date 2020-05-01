package com.tdevilleduc.urthehero.back.service

import com.tdevilleduc.urthehero.back.AbstractTest
import com.tdevilleduc.urthehero.back.BackApplication
import com.tdevilleduc.urthehero.back.dao.EnemyDao
import com.tdevilleduc.urthehero.back.exceptions.PageNotFoundException
import com.tdevilleduc.urthehero.back.model.Enemy
import com.tdevilleduc.urthehero.back.service.impl.EnemyService
import com.tdevilleduc.urthehero.back.util.TestUtil
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.function.Executable
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.*

@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [BackApplication::class])
internal class EnemyServiceTest : AbstractTest() {
    @Autowired
    private lateinit var enemyService: EnemyService
    @Autowired
    private lateinit var enemyDao: EnemyDao


    @Test
    fun test_exists_withIdNull() {
        val enemyId = null
        val exists = enemyService.exists(enemyId)
        Assertions.assertFalse(exists)
    }

    @Test
    fun test_findById_thenCorrect() {
        val enemyId = 1
        val enemy = enemyService.findById(enemyId)
        Assertions.assertNotNull(enemy)
        Assertions.assertEquals(enemyId, enemy.id)
        Assertions.assertEquals("imageBalrog", enemy.image)
        Assertions.assertEquals("Balrog", enemy.name)
        Assertions.assertEquals(6, enemy.level)
        Assertions.assertEquals(25, enemy.lifePoints)
    }

    @Test
    fun test_findById_thenNotFound() {
        val enemyId = 13
        Assertions.assertThrows(PageNotFoundException::class.java, Executable { enemyService.findById(enemyId) } )
    }

    @Test
    fun delete_thenNotFound() {
        val enemyId = 13
        Assertions.assertThrows(PageNotFoundException::class.java, Executable { enemyService.delete(enemyId) })
    }

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

    @Test
    fun test_findByLevel_thenCorrect() {
        val level = 1
        val enemy = enemyService.findByLevel(level)
        Assertions.assertNotNull(enemy)
        Assertions.assertEquals(level, enemy.level)
        Assertions.assertNotNull(enemy.id)
        Assertions.assertNotNull(enemy.image)
        Assertions.assertNotNull(enemy.name)
        Assertions.assertNotNull(enemy.lifePoints)
    }
}