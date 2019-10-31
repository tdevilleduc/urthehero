package com.tdevilleduc.urthehero.back.service;

import com.tdevilleduc.urthehero.back.model.Dice;
import com.tdevilleduc.urthehero.back.model.DiceValue;

public interface IDiceService {

    /**
     * Effectue un lancer de dés avec le dé dice
     * @param dice le type de dé à lancer
     * @return la valeur du dé après lancé
     */
    DiceValue roll(Dice dice);

}
