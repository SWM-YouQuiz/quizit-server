package com.quizit.core.domain.quiz.exception

import com.quizit.core.global.exception.ServerException
import org.springframework.http.HttpStatus

class QuizOptionNotFoundException(
    override val message: String = "퀴즈 선지를 찾을 수 없습니다."
) : ServerException(
        message = message,
        status = HttpStatus.NOT_FOUND
    )
