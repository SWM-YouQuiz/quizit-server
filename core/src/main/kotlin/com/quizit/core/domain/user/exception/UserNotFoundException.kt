package com.quizit.core.domain.user.exception

import com.quizit.core.global.exception.ServerException
import org.springframework.http.HttpStatus

class UserNotFoundException(
    override val message: String = "사용자를 찾을 수 없습니다."
) : ServerException(
        message = message,
        status = HttpStatus.NOT_FOUND
    )
