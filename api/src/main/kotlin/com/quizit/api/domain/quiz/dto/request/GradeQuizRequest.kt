package com.quizit.api.domain.quiz.dto.request

import com.quizit.core.domain.quiz.dto.command.GradeQuizCommand
import java.util.UUID

data class GradeQuizRequest(
    val selectedOptionId: UUID
) {
    fun toCommand(quizId: UUID): GradeQuizCommand =
        GradeQuizCommand(
            quizId = quizId,
            selectedOptionId = selectedOptionId
        )
}
