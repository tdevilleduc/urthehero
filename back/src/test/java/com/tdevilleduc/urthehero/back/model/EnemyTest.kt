package com.tdevilleduc.urthehero.back.model

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.testcontainers.shaded.org.apache.commons.lang.RandomStringUtils
import java.util.*

internal class EnemyTest {
    private val random: Random = Random()

    @Test
    fun test_Constructor() {
        val id = random.nextInt()
        val name = RandomStringUtils.random(20)
        val image = RandomStringUtils.random(20)
        val level = random.nextInt()
        val lifePoints = random.nextInt()
        val enemy = Enemy(id, name, image, level, lifePoints)
        Assertions.assertEquals(enemy.id, id)
        Assertions.assertEquals(enemy.name, name)
        Assertions.assertEquals(enemy.image, image)
        Assertions.assertEquals(enemy.level, level)
        Assertions.assertEquals(enemy.lifePoints, lifePoints)
    }
}