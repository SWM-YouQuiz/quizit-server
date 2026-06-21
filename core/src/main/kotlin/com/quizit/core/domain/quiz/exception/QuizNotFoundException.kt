package com.quizit.core.domain.quiz.exception

import com.quizit.core.global.exception.ServerException
import org.springframework.http.HttpStatus

class QuizNotFoundException(
    override val message: String = "퀴즈를 찾을 수 없습니다."
) : ServerException(
        message = message,
        status = HttpStatus.NOT_FOUND
    )
