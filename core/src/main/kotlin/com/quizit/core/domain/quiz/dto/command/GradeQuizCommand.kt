package com.quizit.core.domain.quiz.dto.command

import java.util.*

data class GradeQuizCommand(
    val quizId: UUID,
    val selectedOptionId: UUID
)
