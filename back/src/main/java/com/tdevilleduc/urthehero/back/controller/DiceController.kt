package com.tdevilleduc.urthehero.back.controller

import com.tdevilleduc.urthehero.back.constant.ApplicationConstants
import com.tdevilleduc.urthehero.back.model.Dice
import com.tdevilleduc.urthehero.back.model.DiceValue
import com.tdevilleduc.urthehero.back.service.IDiceService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.util.Assert
import org.springframework.web.bind.annotation.*
import java.util.*
import java.util.concurrent.Callable
import javax.servlet.http.HttpServletRequest

@Api(value = "Dice", tags = ["Dice Controller"])
@RestController
@RequestMapping("/api/dice")
internal class DiceController(private val diceService: IDiceService) {
    val logger: Logger = LoggerFactory.getLogger(DiceController::class.java)

    @GetMapping(value = ["/roll/{dice}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "\${swagger.controller.dice.roll.value}", notes = "\${swagger.controller.dice.roll.notes}")
    @ResponseBody
    fun roll(request: HttpServletRequest,
             @PathVariable dice: Dice): Callable<ResponseEntity<DiceValue>> = Callable {
        if (logger.isInfoEnabled) {
            logger.info(ApplicationConstants.CONTROLLER_CALL_LOG, request.requestURI)
        }
        Assert.notNull(dice, ApplicationConstants.CHECK_DICE_PARAMETER_MANDATORY!!)
        ResponseEntity.ok(diceService.roll(dice))
    }

    @GetMapping(value = ["/roll/{dice}/{count}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "\${swagger.controller.dice.roll-many.value}", notes = "\${swagger.controller.dice.roll-many.notes}")
    @ResponseBody
    fun roll(request: HttpServletRequest,
             @PathVariable dice: Dice,
             @PathVariable count: Int): Callable<ResponseEntity<MutableList<DiceValue>>> = Callable {
        if (logger.isInfoEnabled) {
            logger.info(ApplicationConstants.CONTROLLER_CALL_LOG, request.requestURI)
        }
        Assert.notNull(dice, ApplicationConstants.CHECK_DICE_PARAMETER_MANDATORY!!)
        Assert.notNull(count, ApplicationConstants.CHECK_COUNT_PARAMETER_MANDATORY!!)
        val diceValues: MutableList<DiceValue> = ArrayList()
        for (i in 0 until count) {
            diceValues.add(diceService.roll(dice))
        }
        ResponseEntity.ok<MutableList<DiceValue>>(diceValues)
    }

}