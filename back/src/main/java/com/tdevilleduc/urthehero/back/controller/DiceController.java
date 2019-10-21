package com.tdevilleduc.urthehero.back.controller;

import com.tdevilleduc.urthehero.back.model.Dice;
import com.tdevilleduc.urthehero.back.model.DiceValue;
import com.tdevilleduc.urthehero.back.service.IDiceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

@Api(value = "Dice", tags = { "Dice Controller" } )
@RestController
@RequestMapping("/Dice")
public class DiceController {

    @Autowired
    private IDiceService diceService;

    @ApiOperation( value = "Effectue un lancer de dés" )
    @GetMapping(value = "/roll/{dice}")
    public Callable<ResponseEntity<DiceValue>> roll(@PathVariable Dice dice) {
        return () -> {
            Assertions.assertNotNull(dice);
            return ResponseEntity.ok(diceService.roll(dice));
        };
    }

    @ApiOperation( value = "Effectue un lancer de dés avec plusieurs dés en même temps" )
    @GetMapping(value = "/roll/{dice}/{count}")
    public Callable<ResponseEntity<List<DiceValue>>> roll(@PathVariable Dice dice, @PathVariable Integer count) {
        return () -> {
            Assertions.assertNotNull(dice);
            Assertions.assertNotNull(count);
            List<DiceValue> diceValues = new ArrayList<>();
            for (int i = 0; i < count; i++) {
                diceValues.add(diceService.roll(dice));
            }

            return ResponseEntity.ok(diceValues);
        };
    }

}
