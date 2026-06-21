package com.quizit.api.domain.quiz.dto.request

import com.quizit.core.domain.quiz.dto.command.ReactQuizCommand
import com.quizit.core.domain.quiz.entity.ReactionType
import java.util.*

data class ReactQuizRequest(
    val reactionType: ReactionType
) {
    fun toCommand(quizId: UUID): ReactQuizCommand =
        ReactQuizCommand(
            quizId = quizId,
            reactionType = reactionType
        )
}
