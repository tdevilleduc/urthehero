package com.tdevilleduc.urthehero.back.service;

import com.tdevilleduc.urthehero.back.model.Dice;
import com.tdevilleduc.urthehero.back.model.DiceValue;
import com.tdevilleduc.urthehero.back.service.impl.DiceService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DiceServiceTest {

    private DiceService diceService;

    @BeforeEach
    public void onSetupClass() {
        diceService = new DiceService();
    }

    @AfterEach
    public void onTeardownClass() {
        diceService = null;
    }

    @Test
    public void test_roll6_thenCorrect() {
        assertRollDice(Dice.DE_6);
    }

    @Test
    public void test_roll10_thenCorrect() {
        assertRollDice(Dice.DE_10);
    }

    @Test
    public void test_roll20_thenCorrect() {
        assertRollDice(Dice.DE_20);
    }

    private void assertRollDice(Dice dice) {
        DiceValue diceValue = diceService.roll(dice);
        Assertions.assertNotNull(diceValue);
        Assertions.assertEquals(dice, diceValue.getDice());
        Assertions.assertTrue(diceValue.getValue() <= dice.getValue());
        Assertions.assertTrue(diceValue.getValue() > 0);
    }

}
