package com.tdevilleduc.urthehero.back.service.impl

import com.tdevilleduc.urthehero.back.model.Dice
import com.tdevilleduc.urthehero.back.model.DiceValue
import com.tdevilleduc.urthehero.back.service.IDiceService
import org.apache.commons.math3.random.RandomDataGenerator
import org.springframework.stereotype.Service

@Service
class DiceService : IDiceService {
    override fun roll(dice: Dice): DiceValue {
        return DiceValue(generatingRandomIntegerBounded(dice.value), dice)
    }

    companion object {
        fun generatingRandomIntegerBounded(rightLimit: Int): Int {
            return RandomDataGenerator().nextInt(1, rightLimit)
        }
    }
}