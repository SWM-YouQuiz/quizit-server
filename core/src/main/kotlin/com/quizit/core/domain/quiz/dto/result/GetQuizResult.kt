package com.quizit.core.domain.quiz.dto.result

import java.util.*

data class GetQuizResult(
    val id: UUID,
    val chapterId: UUID,
    val question: String,
    val solution: String?,
    val options: List<QuizOption>,
    val correctCount: Long,
    val incorrectCount: Long,
    val selectedOptionId: UUID?,
    val isCorrect: Boolean?
) {
    data class QuizOption(
        val id: UUID,
        val content: String,
        val isAnswer: Boolean?
    )
}
