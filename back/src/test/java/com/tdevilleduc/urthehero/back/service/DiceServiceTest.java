package com.tdevilleduc.urthehero.back.service;

import com.tdevilleduc.urthehero.back.model.Dice;
import com.tdevilleduc.urthehero.back.model.DiceValue;
import com.tdevilleduc.urthehero.back.service.impl.DiceService;
import com.tdevilleduc.urthehero.back.service.impl.NextPageService;
import org.junit.*;

public class DiceServiceTest {

    private DiceService diceService;

    @Before
    public void onSetupClass() {
        diceService = new DiceService();
    }

    @After
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
        Assert.assertNotNull(diceValue);
        Assert.assertEquals(dice, diceValue.getDice());
        Assert.assertTrue(diceValue.getValue() <= dice.getValue());
        Assert.assertTrue(diceValue.getValue() > 0);
    }

}
