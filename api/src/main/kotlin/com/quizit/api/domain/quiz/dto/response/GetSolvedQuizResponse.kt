package com.quizit.api.domain.quiz.dto.response

import com.quizit.core.domain.quiz.dto.result.GetSolvedQuizResult
import java.util.*

data class GetSolvedQuizResponse(
    val id: UUID,
    val chapterId: UUID,
    val question: String,
    val solution: String,
    val options: List<QuizOptionResponse>,
    val correctCount: Long,
    val incorrectCount: Long,
    val selectedOptionId: UUID,
    val isCorrect: Boolean
) {
    data class QuizOptionResponse(
        val id: UUID,
        val content: String,
        val isAnswer: Boolean
    )

    companion object {
        fun from(result: GetSolvedQuizResult): GetSolvedQuizResponse =
            with(result) {
                GetSolvedQuizResponse(
                    id = id,
                    chapterId = chapterId,
                    question = question,
                    solution = solution,
                    options =
                        options.map {
                            with(it) {
                                QuizOptionResponse(
                                    id = id,
                                    content = content,
                                    isAnswer = isAnswer
                                )
                            }
                        },
                    correctCount = correctCount,
                    incorrectCount = incorrectCount,
                    selectedOptionId = selectedOptionId,
                    isCorrect = isCorrect
                )
            }
    }
}
