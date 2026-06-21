package com.quizit.api.domain.quiz.dto.response

import com.quizit.core.domain.quiz.dto.result.GradeQuizResult

data class GradeQuizResponse(
    val isCorrect: Boolean,
    val solution: String
) {
    companion object {
        fun from(result: GradeQuizResult): GradeQuizResponse =
            with(result) {
                GradeQuizResponse(
                    isCorrect = isCorrect,
                    solution = solution
                )
            }
    }
}
