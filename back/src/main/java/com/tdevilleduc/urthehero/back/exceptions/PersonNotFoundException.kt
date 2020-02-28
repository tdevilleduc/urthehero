package com.tdevilleduc.urthehero.back.exceptions

import lombok.NoArgsConstructor
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@NoArgsConstructor
@ResponseStatus(HttpStatus.NOT_FOUND)
class PersonNotFoundException(s: String?) : RuntimeException(s)