package com.quizit.api.domain.quiz.dto.response

import com.quizit.core.domain.quiz.dto.result.GetQuizResult
import java.util.*

data class GetQuizResponse(
    val id: UUID,
    val question: String,
    val solution: String?,
    val chapterId: UUID,
    val options: List<QuizOptionResponse>,
    val correctCount: Long,
    val incorrectCount: Long,
    val selectedOptionId: UUID?,
    val isCorrect: Boolean?
) {
    data class QuizOptionResponse(
        val id: UUID,
        val content: String,
        val isAnswer: Boolean?
    )

    companion object {
        fun from(result: GetQuizResult): GetQuizResponse =
            with(result) {
                GetQuizResponse(
                    id = id,
                    question = question,
                    solution = solution,
                    chapterId = chapterId,
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
