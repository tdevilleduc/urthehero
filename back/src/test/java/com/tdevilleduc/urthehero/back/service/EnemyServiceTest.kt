package com.tdevilleduc.urthehero.back.service

import com.tdevilleduc.urthehero.back.dao.EnemyDao
import com.tdevilleduc.urthehero.back.exceptions.EnemyNotFoundException
import com.tdevilleduc.urthehero.back.service.impl.EnemyService
import com.tdevilleduc.urthehero.back.util.TestUtil
import org.junit.Before
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*

@ExtendWith(MockitoExtension::class)
internal class EnemyServiceTest {

    private val random: Random = Random()

    @Mock
    private lateinit var enemyDao: EnemyDao
    @InjectMocks
    private lateinit var enemyService: EnemyService

    @Before
    fun initMocks() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun test_exists_withIdNull() {
        val enemyId = null
        val exists = enemyService.exists(enemyId)
        Assertions.assertFalse(exists)
    }

    @Test
    fun test_notExists_thenSuccess() {
        val enemyId = random.nextInt()
        Mockito.`when`(enemyDao.findById(enemyId)).thenReturn(Optional.empty())

        val notExists = enemyService.notExists(enemyId)
        Assertions.assertTrue(notExists)
    }

    @Test
    fun test_findById_thenCorrect() {
        val expectedEnemy = TestUtil.createEnemy()
        Mockito.`when`(enemyDao.findById(expectedEnemy.id)).thenReturn(Optional.of(expectedEnemy))

        val enemy = enemyService.findById(expectedEnemy.id)
        Assertions.assertNotNull(enemy)
        Assertions.assertEquals(expectedEnemy.id, enemy.id)
        Assertions.assertEquals(expectedEnemy.image, enemy.image)
        Assertions.assertEquals(expectedEnemy.name, enemy.name)
        Assertions.assertEquals(expectedEnemy.level, enemy.level)
        Assertions.assertEquals(expectedEnemy.lifePoints, enemy.lifePoints)
    }

    @Test
    fun test_findById_thenNotFound() {
        val enemyId = random.nextInt()
        Mockito.`when`(enemyDao.findById(enemyId)).thenReturn(Optional.empty())

        Assertions.assertThrows(EnemyNotFoundException::class.java) { enemyService.findById(enemyId) }
    }

    @Test
    fun delete_thenNotFound() {
        val enemyId = random.nextInt()
        Mockito.`when`(enemyDao.findById(enemyId)).thenReturn(Optional.empty())

        Assertions.assertThrows(EnemyNotFoundException::class.java) { enemyService.delete(enemyId) }
    }

    @Test
    fun test_findByLevel_thenCorrect() {
        val level = random.nextInt(Integer.SIZE - 1)
        val expectedEnemy1 = TestUtil.createEnemy()
        expectedEnemy1.level = level
        val expectedEnemy2 = TestUtil.createEnemy()
        expectedEnemy2.level = level
        val listOfEnemy = mutableListOf(expectedEnemy1, expectedEnemy2)
        Mockito.`when`(enemyDao.findByLevel(Mockito.anyInt())).thenReturn(listOfEnemy)

        val enemy = enemyService.findByLevel(level)
        Assertions.assertNotNull(enemy)
        Assertions.assertEquals(level, enemy.level)
        Assertions.assertNotNull(enemy.id)
        Assertions.assertNotNull(enemy.image)
        Assertions.assertNotNull(enemy.name)
        Assertions.assertNotNull(enemy.lifePoints)
        Assertions.assertTrue(listOfEnemy.contains(enemy))
    }

    @Test
    fun test_findByLevel_withAll_thenCorrect() {
        val level = 0
        val expectedEnemy1 = TestUtil.createEnemy()
        val expectedEnemy2 = TestUtil.createEnemy()
        val listOfEnemy = mutableListOf(expectedEnemy1, expectedEnemy2)
        Mockito.`when`(enemyDao.findAll()).thenReturn(listOfEnemy)

        val enemy = enemyService.findByLevel(level)
        Assertions.assertNotNull(enemy)
        Assertions.assertNotNull(enemy.id)
        Assertions.assertNotNull(enemy.level)
        Assertions.assertNotNull(enemy.image)
        Assertions.assertNotNull(enemy.name)
        Assertions.assertNotNull(enemy.lifePoints)
        Assertions.assertTrue(listOfEnemy.contains(enemy))
    }
}