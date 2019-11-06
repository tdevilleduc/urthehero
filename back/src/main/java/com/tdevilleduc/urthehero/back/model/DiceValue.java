package com.tdevilleduc.urthehero.back.model;

import lombok.*;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class DiceValue {

    @NotNull
    private Integer value;
    @NotNull
    private Dice dice;

}
