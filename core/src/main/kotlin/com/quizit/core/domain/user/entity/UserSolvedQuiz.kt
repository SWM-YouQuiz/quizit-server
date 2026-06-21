package com.quizit.core.domain.user.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.util.*

@Table("user_solved_quiz")
class UserSolvedQuiz(
    @Id var id: UUID? = null,
    val userId: UUID,
    val quizId: UUID,
    val selectedOptionId: UUID? = null,
    val isCorrect: Boolean
)
