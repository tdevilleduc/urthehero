package com.tdevilleduc.urthehero.back.service

import com.tdevilleduc.urthehero.back.AbstractTest
import com.tdevilleduc.urthehero.back.BackApplication
import com.tdevilleduc.urthehero.back.model.Dice
import com.tdevilleduc.urthehero.back.service.impl.DiceService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [BackApplication::class])
internal class DiceServiceTest : AbstractTest() {

    @Autowired
    private lateinit var diceService: DiceService

    @Test
    fun test_roll6_thenCorrect() {
        assertRollDice(Dice.DE_6)
    }

    @Test
    fun test_roll10_thenCorrect() {
        assertRollDice(Dice.DE_10)
    }

    @Test
    fun test_roll20_thenCorrect() {
        assertRollDice(Dice.DE_20)
    }

    private fun assertRollDice(dice: Dice) {
        val diceValue = diceService.roll(dice)
        Assertions.assertNotNull(diceValue)
        Assertions.assertEquals(dice, diceValue.dice)
        Assertions.assertTrue(diceValue.value <= dice.value)
        Assertions.assertTrue(diceValue.value > 0)
    }
}