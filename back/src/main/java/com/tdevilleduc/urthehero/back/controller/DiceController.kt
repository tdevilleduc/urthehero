package com.tdevilleduc.urthehero.back.controller

import com.tdevilleduc.urthehero.back.model.Dice
import com.tdevilleduc.urthehero.back.model.DiceValue
import com.tdevilleduc.urthehero.back.service.IDiceService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import java.util.concurrent.Callable

@Tag(name = "Dice", description = "Dice Controller")
@RestController
@RequestMapping("/api/dice")
@SecurityRequirement(name = "bearerAuth")
internal class DiceController(private val diceService: IDiceService) {

    @GetMapping(value = ["/roll/{dice}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "\${swagger.controller.dice.roll.value}", description = "\${swagger.controller.dice.roll.notes}")
    @ResponseBody
    fun rollOneDice(@PathVariable dice: Dice): Callable<ResponseEntity<DiceValue>> = Callable {
        ResponseEntity.ok(diceService.roll(dice))
    }

    @GetMapping(value = ["/roll/{dice}/{count}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "\${swagger.controller.dice.roll-many.value}", description = "\${swagger.controller.dice.roll-many.notes}")
    @ResponseBody
    fun rollManyDices(@PathVariable dice: Dice,
                      @PathVariable count: Int): Callable<ResponseEntity<MutableList<DiceValue>>> = Callable {
        val diceValues: MutableList<DiceValue> = ArrayList()
        for (i in 0 until count) {
            diceValues.add(diceService.roll(dice))
        }
        ResponseEntity.ok(diceValues)
    }

}