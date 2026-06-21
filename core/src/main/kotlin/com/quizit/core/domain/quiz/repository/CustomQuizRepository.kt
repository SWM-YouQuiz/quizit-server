package com.quizit.core.domain.quiz.repository

import com.quizit.core.domain.quiz.dto.projection.QuizDetailProjection
import com.quizit.core.domain.quiz.dto.projection.SolvedQuizDetailProjection
import java.util.*

interface CustomQuizRepository {
    fun findQuizDetails(
        userId: UUID,
        chapterId: UUID
    ): List<QuizDetailProjection>

    fun findSolvedQuizDetails(
        userId: UUID,
        isCorrect: Boolean?
    ): List<SolvedQuizDetailProjection>
}
