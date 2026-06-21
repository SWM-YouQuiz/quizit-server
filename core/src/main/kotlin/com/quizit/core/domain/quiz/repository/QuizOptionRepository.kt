package com.quizit.core.domain.quiz.repository

import com.quizit.core.domain.quiz.entity.QuizOption
import com.quizit.core.global.repository.JdbcRepository
import java.util.*

interface QuizOptionRepository : JdbcRepository<QuizOption, UUID> {
    fun findByIdAndQuizId(
        id: UUID,
        quizId: UUID
    ): QuizOption?

    fun findAllByQuizIdIn(quizIds: Collection<UUID>): List<QuizOption>
}
