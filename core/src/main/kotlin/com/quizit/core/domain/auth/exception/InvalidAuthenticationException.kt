package com.quizit.core.domain.auth.exception

import com.quizit.core.global.exception.ServerException
import org.springframework.http.HttpStatus

class InvalidAuthenticationException(
    override val message: String = "유효하지 않은 인증 토큰입니다."
) : ServerException(
        message = message,
        status = HttpStatus.UNAUTHORIZED
    )
