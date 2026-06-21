package com.quizit.core.domain.quiz.dto.result

import com.quizit.core.domain.quiz.dto.projection.SolvedQuizDetailProjection
import java.util.*
import com.quizit.core.domain.quiz.entity.QuizOption as QuizOptionEntity

data class GetSolvedQuizResult(
    val id: UUID,
    val chapterId: UUID,
    val question: String,
    val solution: String,
    val options: List<GetSolvedQuizResult.QuizOption>,
    val correctCount: Long,
    val incorrectCount: Long,
    val selectedOptionId: UUID,
    val isCorrect: Boolean
) {
    data class QuizOption(
        val id: UUID,
        val content: String,
        val isAnswer: Boolean
    ) {
        companion object {
            fun from(option: QuizOptionEntity): GetSolvedQuizResult.QuizOption =
                with(option) {
                    QuizOption(
                        id = id!!,
                        content = content,
                        isAnswer = isAnswer
                    )
                }
        }
    }

    companion object {
        fun of(
            solvedQuizDetail: SolvedQuizDetailProjection,
            options: List<QuizOptionEntity>
        ): GetSolvedQuizResult =
            with(solvedQuizDetail) {
                GetSolvedQuizResult(
                    id = id,
                    chapterId = chapterId,
                    question = question,
                    solution = solution,
                    options = options.map { QuizOption.from(it) },
                    correctCount = correctCount,
                    incorrectCount = incorrectCount,
                    selectedOptionId = selectedOptionId,
                    isCorrect = isCorrect
                )
            }
    }
}
