package com.quizit.core.domain.quiz.service

import com.quizit.core.domain.quiz.dto.query.GetSolvedQuizzesQuery
import com.quizit.core.domain.quiz.dto.result.GetQuizResult
import com.quizit.core.domain.quiz.dto.result.GetSolvedQuizResult
import com.quizit.core.domain.quiz.repository.QuizOptionRepository
import com.quizit.core.domain.quiz.repository.QuizRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class QuizService(
    private val quizRepository: QuizRepository,
    private val quizOptionRepository: QuizOptionRepository
) {
    @Transactional(readOnly = true)
    fun getQuizzesByChapterId(
        userId: UUID,
        chapterId: UUID
    ): List<GetQuizResult> {
        val quizDetails =
            quizRepository.findQuizDetails(
                userId = userId,
                chapterId = chapterId
            )
        val quizOptions =
            quizOptionRepository.findAllByQuizIdIn(quizDetails.map { it.id })
                .groupBy { it.quizId }

        return quizDetails.map { quizDetail ->
            val isSolved = quizDetail.isCorrect != null

            GetQuizResult(
                id = quizDetail.id,
                chapterId = quizDetail.chapterId,
                question = quizDetail.question,
                solution = quizDetail.solution.takeIf { isSolved },
                options =
                    quizOptions.getValue(quizDetail.id)
                        .map {
                            GetQuizResult.QuizOption(
                                id = it.id!!,
                                content = it.content,
                                isAnswer = it.isAnswer.takeIf { isSolved }
                            )
                        },
                correctCount = quizDetail.correctCount,
                incorrectCount = quizDetail.incorrectCount,
                selectedOptionId = quizDetail.selectedOptionId,
                isCorrect = quizDetail.isCorrect
            )
        }
    }

    @Transactional(readOnly = true)
    fun getSolvedQuizzes(
        userId: UUID,
        query: GetSolvedQuizzesQuery
    ): List<GetSolvedQuizResult> {
        val solvedQuizDetails =
            quizRepository.findSolvedQuizDetails(
                userId = userId,
                isCorrect = query.isCorrect
            )
        val quizOptions =
            quizOptionRepository.findAllByQuizIdIn(solvedQuizDetails.map { it.id })
                .groupBy { it.quizId }

        return solvedQuizDetails.map {
            GetSolvedQuizResult.of(
                solvedQuizDetail = it,
                options = quizOptions.getValue(it.id)
            )
        }
    }
}
