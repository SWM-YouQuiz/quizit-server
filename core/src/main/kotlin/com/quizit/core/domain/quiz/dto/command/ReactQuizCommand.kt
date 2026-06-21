package com.quizit.core.domain.quiz.dto.command

import com.quizit.core.domain.quiz.entity.ReactionType
import java.util.*

data class ReactQuizCommand(
    val quizId: UUID,
    val reactionType: ReactionType
)
