package com.tdevilleduc.urthehero.back.service.impl;

import com.tdevilleduc.urthehero.back.model.Dice;
import com.tdevilleduc.urthehero.back.model.DiceValue;
import com.tdevilleduc.urthehero.back.service.IDiceService;
import org.apache.commons.math3.random.RandomDataGenerator;
import org.springframework.stereotype.Service;

@Service
public class DiceService implements IDiceService {

    @Override
    public DiceValue roll(Dice dice) {
        return new DiceValue(generatingRandomIntegerBounded(dice.getValue()), dice);
    }

    private Integer generatingRandomIntegerBounded(Integer rightLimit) {
        return new RandomDataGenerator().nextInt(1, rightLimit);
    }
}
