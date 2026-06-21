package com.quizit.core.domain.user.repository

import com.quizit.core.domain.user.entity.UserSolvedQuiz
import com.quizit.core.global.repository.JdbcRepository
import java.util.*

interface UserSolvedQuizRepository : JdbcRepository<UserSolvedQuiz, UUID> {
    fun existsByUserIdAndQuizId(
        userId: UUID,
        quizId: UUID
    ): Boolean
}
