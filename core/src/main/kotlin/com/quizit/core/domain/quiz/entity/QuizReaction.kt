package com.quizit.core.domain.quiz.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.util.*

@Table("quiz_reaction")
class QuizReaction(
    @Id var id: UUID? = null,
    val quizId: UUID,
    val userId: UUID,
    var reactionType: ReactionType
)
