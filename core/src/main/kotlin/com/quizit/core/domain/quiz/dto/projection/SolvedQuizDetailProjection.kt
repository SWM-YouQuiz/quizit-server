package com.quizit.core.domain.quiz.dto.projection

import java.util.*

data class SolvedQuizDetailProjection(
    val id: UUID,
    val chapterId: UUID,
    val question: String,
    val solution: String,
    val correctCount: Long,
    val incorrectCount: Long,
    val selectedOptionId: UUID,
    val isCorrect: Boolean
)
