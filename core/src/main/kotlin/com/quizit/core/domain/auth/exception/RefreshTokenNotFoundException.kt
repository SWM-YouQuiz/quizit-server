package com.quizit.core.domain.auth.exception

import com.quizit.core.global.exception.ServerException
import org.springframework.http.HttpStatus

class RefreshTokenNotFoundException(
    override val message: String = "리프레시 토큰을 찾을 수 없습니다."
) : ServerException(
        message = message,
        status = HttpStatus.UNAUTHORIZED
    )
