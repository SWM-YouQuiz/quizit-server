package com.quizit.api.fixture

import com.quizit.api.domain.quiz.dto.request.GradeQuizRequest
import com.quizit.api.domain.quiz.dto.request.ReactQuizRequest
import com.quizit.api.domain.quiz.dto.response.GetQuizResponse
import com.quizit.api.domain.quiz.dto.response.GetSolvedQuizResponse
import com.quizit.api.domain.quiz.dto.response.GradeQuizResponse
import com.quizit.core.domain.quiz.entity.ReactionType
import com.quizit.core.fixture.QUIZ_CHAPTER_ID
import com.quizit.core.fixture.QUIZ_ID
import com.quizit.core.fixture.QUIZ_OPTIONS
import com.quizit.core.fixture.QUIZ_OPTION_ID
import com.quizit.core.fixture.QUIZ_QUESTION
import com.quizit.core.fixture.QUIZ_SOLUTION
import com.quizit.core.fixture.UPDATED_REACTION_TYPE
import java.util.UUID

fun createGetQuizResponse(
    id: UUID = QUIZ_ID,
    question: String = QUIZ_QUESTION,
    solution: String? = QUIZ_SOLUTION,
    chapterId: UUID = QUIZ_CHAPTER_ID,
    options: List<GetQuizResponse.QuizOptionResponse> =
        listOf(
            GetQuizResponse.QuizOptionResponse(
                id = QUIZ_OPTION_ID,
                content = QUIZ_OPTIONS.first(),
                isAnswer = true
            )
        ),
    correctCount: Long = 1,
    incorrectCount: Long = 1,
    selectedOptionId: UUID? = QUIZ_OPTION_ID,
    isCorrect: Boolean? = true
): GetQuizResponse =
    GetQuizResponse(
        id = id,
        question = question,
        solution = solution,
        chapterId = chapterId,
        options = options,
        correctCount = correctCount,
        incorrectCount = incorrectCount,
        selectedOptionId = selectedOptionId,
        isCorrect = isCorrect
    )

fun createGetQuizResponses(): List<GetQuizResponse> = listOf(createGetQuizResponse())

fun createGetSolvedQuizResponses(): List<GetSolvedQuizResponse> = emptyList()

fun createReactQuizRequest(reactionType: ReactionType = UPDATED_REACTION_TYPE): ReactQuizRequest =
    ReactQuizRequest(reactionType = reactionType)

fun createGradeQuizRequest(selectedOptionId: UUID = QUIZ_OPTION_ID): GradeQuizRequest =
    GradeQuizRequest(selectedOptionId = selectedOptionId)

fun createGradeQuizResponse(
    isCorrect: Boolean = true,
    solution: String = QUIZ_SOLUTION
): GradeQuizResponse =
    GradeQuizResponse(
        isCorrect = isCorrect,
        solution = solution
    )
