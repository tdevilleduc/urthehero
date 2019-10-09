package com.tdevilleduc.urthehero.back.controller;

import com.tdevilleduc.urthehero.back.model.Dice;
import com.tdevilleduc.urthehero.back.model.DiceValue;
import com.tdevilleduc.urthehero.back.model.Page;
import com.tdevilleduc.urthehero.back.service.IDiceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.math3.random.RandomDataGenerator;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@Api(value = "Dice", tags = { "Dice Controller" } )
@RestController
@RequestMapping("/Dice")
public class DiceController {

    @Autowired
    private IDiceService diceService;

    @ApiOperation( value = "Effectue un lancer de dés" )
    @PostMapping(value = "/roll/{dice}")
    public DiceValue roll(@PathVariable Dice dice) {
        Assert.assertNotNull(dice);
        return diceService.roll(dice);
    }

}
