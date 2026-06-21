package com.quizit.core.domain.quiz.repository

import com.quizit.core.domain.quiz.entity.QuizBookmark
import com.quizit.core.global.repository.JdbcRepository
import java.util.UUID

interface QuizBookmarkRepository : JdbcRepository<QuizBookmark, UUID> {
    fun existsByQuizIdAndUserId(
        quizId: UUID,
        userId: UUID
    ): Boolean

    fun deleteByQuizIdAndUserId(
        quizId: UUID,
        userId: UUID
    )
}
