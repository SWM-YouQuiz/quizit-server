package com.quizit.core.domain.quiz.repository

import com.quizit.core.domain.quiz.entity.QuizReaction
import com.quizit.core.global.repository.JdbcRepository
import java.util.*

interface QuizReactionRepository : JdbcRepository<QuizReaction, UUID> {
    fun findByQuizIdAndUserId(
        quizId: UUID,
        userId: UUID
    ): QuizReaction?
}
