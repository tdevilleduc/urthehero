package com.tdevilleduc.urthehero.back.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
class StoryInternalErrorException(s: String?) : RuntimeException(s)