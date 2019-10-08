package com.tdevilleduc.urthehero.back.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Dice {

    DE_20(20), DE_10(10), DE_6(6);

    @Getter
    private Integer value;
}
