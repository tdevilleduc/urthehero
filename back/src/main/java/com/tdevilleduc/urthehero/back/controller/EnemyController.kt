package com.tdevilleduc.urthehero.back.controller

import com.tdevilleduc.urthehero.back.constant.ApplicationConstants
import com.tdevilleduc.urthehero.back.exceptions.EnemyInternalErrorException
import com.tdevilleduc.urthehero.back.model.Enemy
import com.tdevilleduc.urthehero.back.model.EnemyDTO
import com.tdevilleduc.urthehero.back.service.IEnemyService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.helpers.MessageFormatter
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.util.Assert
import org.springframework.web.bind.annotation.*
import java.util.concurrent.Callable

@Tag(name = "Enemy", description = "Enemy Controller")
@RestController
@RequestMapping("/api/enemy")
@SecurityRequirement(name = "bearerAuth")
internal class EnemyController(private val enemyService: IEnemyService) {

    @GetMapping(value = ["/{enemyId}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.OK)
//    @Operation(summary = "\${swagger.controller.enemy.get-by-id.value}", description = "\${swagger.controller.enemy.get-by-id.notes}")
    fun getById(@PathVariable enemyId: Int): Callable<ResponseEntity<Enemy>> = Callable {
        ResponseEntity.ok(enemyService.findById(enemyId))
    }

    @GetMapping(value = ["/level/{enemyLevel}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.OK)
//    @Operation(summary = "\${swagger.controller.enemy.get-by-level.value}", description = "\${swagger.controller.enemy.get-by-level.notes}")
    fun getByLevel(@PathVariable enemyLevel: Int): Callable<ResponseEntity<Enemy>> = Callable {
        ResponseEntity.ok(enemyService.findByLevel(enemyLevel))
    }

    @PutMapping
//    @Operation(summary = "\${swagger.controller.enemy.create.value}", description = "\${swagger.controller.enemy.create.notes}")
    fun create(@RequestBody enemyDto: EnemyDTO): Callable<ResponseEntity<EnemyDTO>> = Callable {
        if (enemyService.exists(enemyDto.id)) {
            throw EnemyInternalErrorException(MessageFormatter.format(ApplicationConstants.ERROR_MESSAGE_ENEMY_ID_ALREADY_EXISTS, enemyDto.id).message)
        }
        ResponseEntity.ok(enemyService.createOrUpdate(enemyDto))
    }

    @PostMapping
//    @Operation(summary = "\${swagger.controller.enemy.update.value}", description = "\${swagger.controller.enemy.update.notes}")
    fun update(@RequestBody enemyDto: EnemyDTO): Callable<ResponseEntity<EnemyDTO>> = Callable {
        Assert.notNull(enemyDto.id) { throw EnemyInternalErrorException(ApplicationConstants.ERROR_MESSAGE_ENEMY_ID_CANNOT_BE_NULL) }
        if (enemyService.notExists(enemyDto.id)) {
            throw EnemyInternalErrorException(MessageFormatter.format(ApplicationConstants.ERROR_MESSAGE_ENEMY_ID_DOESNOT_EXIST, enemyDto.id).message)
        }
        ResponseEntity.ok(enemyService.createOrUpdate(enemyDto))
    }

    @DeleteMapping(value = ["/{enemyId}"])
//    @Operation(summary = "\${swagger.controller.enemy.delete.value}", description = "\${swagger.controller.enemy.delete.notes}")
    fun delete(@PathVariable enemyId: Int) = Callable {
        enemyService.delete(enemyId)
    }

}