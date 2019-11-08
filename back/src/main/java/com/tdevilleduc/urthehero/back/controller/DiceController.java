package com.tdevilleduc.urthehero.back.controller;

import com.tdevilleduc.urthehero.back.model.Dice;
import com.tdevilleduc.urthehero.back.model.DiceValue;
import com.tdevilleduc.urthehero.back.service.IDiceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

@Slf4j
@Api(value = "Dice", tags = { "Dice Controller" } )
@RestController
@RequestMapping("/api/dice")
class DiceController {

    private final IDiceService diceService;

    public DiceController(IDiceService diceService) {
        this.diceService = diceService;
    }

    @GetMapping(value = "/roll/{dice}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(
            value = "${swagger.controller.dice.roll.value}",
            notes = "${swagger.controller.dice.roll.notes}")
    public @ResponseBody Callable<ResponseEntity<DiceValue>> roll(HttpServletRequest request,
                                                                  @PathVariable Dice dice) {
        return () -> {
            if (log.isInfoEnabled()) {
                log.info("call: {}", request.getRequestURI());
            }
            Assert.notNull(dice, "The dice parameter is mandatory !");
            return ResponseEntity.ok(diceService.roll(dice));
        };
    }

    @GetMapping(value = "/roll/{dice}/{count}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(
            value = "${swagger.controller.dice.roll-many.value}",
            notes = "${swagger.controller.dice.roll-many.notes}")
    public @ResponseBody Callable<ResponseEntity<List<DiceValue>>> roll(HttpServletRequest request,
                                                                        @PathVariable Dice dice,
                                                                        @PathVariable Integer count) {
        return () -> {
            if (log.isInfoEnabled()) {
                log.info("call: {}", request.getRequestURI());
            }
            Assert.notNull(dice, "The dice parameter is mandatory !");
            Assert.notNull(count, "The dice parameter is mandatory !");
            List<DiceValue> diceValues = new ArrayList<>();
            for (int i = 0; i < count; i++) {
                diceValues.add(diceService.roll(dice));
            }

            return ResponseEntity.ok(diceValues);
        };
    }

}
