package com.quizit.api.global.exception

import com.quizit.core.global.exception.ServerException
import org.springframework.http.HttpStatus

class InvalidRequestException(
    override val message: String = "잘못된 요청입니다."
) : ServerException(
        message = message,
        status = HttpStatus.BAD_REQUEST
    )
