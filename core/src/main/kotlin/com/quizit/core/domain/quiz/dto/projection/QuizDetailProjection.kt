package com.quizit.core.domain.quiz.dto.projection

import java.util.*

data class QuizDetailProjection(
    val id: UUID,
    val chapterId: UUID,
    val question: String,
    val solution: String,
    val correctCount: Long,
    val incorrectCount: Long,
    val selectedOptionId: UUID?,
    val isCorrect: Boolean?
)
