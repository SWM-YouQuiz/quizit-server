package com.quizit.core.fixture

import com.quizit.core.domain.quiz.dto.command.GradeQuizCommand
import com.quizit.core.domain.quiz.dto.command.MarkQuizCommand
import com.quizit.core.domain.quiz.dto.command.ReactQuizCommand
import com.quizit.core.domain.quiz.dto.projection.QuizDetailProjection
import com.quizit.core.domain.quiz.dto.projection.SolvedQuizDetailProjection
import com.quizit.core.domain.quiz.dto.query.GetSolvedQuizzesQuery
import com.quizit.core.domain.quiz.dto.result.GetQuizResult
import com.quizit.core.domain.quiz.dto.result.GetSolvedQuizResult
import com.quizit.core.domain.quiz.dto.result.GradeQuizResult
import com.quizit.core.domain.quiz.entity.Quiz
import com.quizit.core.domain.quiz.entity.QuizOption
import com.quizit.core.domain.quiz.entity.QuizReaction
import com.quizit.core.domain.quiz.entity.ReactionType
import java.util.*

val QUIZ_ID: UUID = UUID.fromString("0198fb75-7cc9-7d43-b7cc-f05d161d5c42")
val QUIZ_CHAPTER_ID: UUID = UUID.fromString("0198fb75-7cc9-7d43-b7cc-f05d161d5c40")
const val QUIZ_QUESTION: String = "Spring Boot의 DI 컨테이너는 무엇인가요?"
const val QUIZ_SOLUTION: String = "ApplicationContext는 Spring의 DI 컨테이너입니다."
val QUIZ_OPTIONS: List<String> = listOf("ApplicationContext", "DispatcherServlet", "EntityManager", "DataSource")
val QUIZ_OPTION_ID: UUID = UUID.fromString("0198fb75-7cc9-7d43-b7cc-f05d161d5c45")
const val QUIZ_RESULT_SIZE: Int = 1
const val QUIZ_CORRECT_COUNT: Long = 1L
const val QUIZ_INCORRECT_COUNT: Long = 0L
const val QUIZ_OTHER_INCORRECT_COUNT: Long = 1L
const val QUIZ_DETAIL_RESULT_SIZE: Int = 1
const val SOLVED_QUIZ_FILTER: Boolean = true
const val INCORRECT_QUIZ_FILTER: Boolean = false
val INITIAL_REACTION_TYPE: ReactionType = ReactionType.LIKE
val UPDATED_REACTION_TYPE: ReactionType = ReactionType.DISLIKE

fun createQuiz(
    id: UUID? = QUIZ_ID,
    question: String = QUIZ_QUESTION,
    solution: String = QUIZ_SOLUTION,
    chapterId: UUID = QUIZ_CHAPTER_ID
): Quiz =
    Quiz(
        id = id,
        question = question,
        solution = solution,
        chapterId = chapterId
    )

fun createGetQuizResult(
    id: UUID = QUIZ_ID,
    chapterId: UUID = QUIZ_CHAPTER_ID,
    question: String = QUIZ_QUESTION,
    solution: String? = QUIZ_SOLUTION,
    options: List<GetQuizResult.QuizOption> =
        listOf(
            GetQuizResult.QuizOption(
                id = QUIZ_OPTION_ID,
                content = QUIZ_OPTIONS.first(),
                isAnswer = true
            )
        ),
    correctCount: Long = 1,
    incorrectCount: Long = 1,
    selectedOptionId: UUID? = QUIZ_OPTION_ID,
    isCorrect: Boolean? = true
): GetQuizResult =
    GetQuizResult(
        id = id,
        chapterId = chapterId,
        question = question,
        solution = solution,
        options = options,
        correctCount = correctCount,
        incorrectCount = incorrectCount,
        selectedOptionId = selectedOptionId,
        isCorrect = isCorrect
    )

fun createGradeQuizResult(
    isCorrect: Boolean = true,
    solution: String = QUIZ_SOLUTION
): GradeQuizResult =
    GradeQuizResult(
        isCorrect = isCorrect,
        solution = solution
    )

fun createGradeQuizCommand(
    quizId: UUID = QUIZ_ID,
    selectedOptionId: UUID = QUIZ_OPTION_ID
): GradeQuizCommand =
    GradeQuizCommand(
        quizId = quizId,
        selectedOptionId = selectedOptionId
    )

fun createMarkQuizCommand(quizId: UUID = QUIZ_ID): MarkQuizCommand = MarkQuizCommand(quizId = quizId)

fun createReactQuizCommand(
    quizId: UUID = QUIZ_ID,
    reactionType: ReactionType = UPDATED_REACTION_TYPE
): ReactQuizCommand =
    ReactQuizCommand(
        quizId = quizId,
        reactionType = reactionType
    )

fun createGetSolvedQuizzesQuery(isCorrect: Boolean? = SOLVED_QUIZ_FILTER): GetSolvedQuizzesQuery =
    GetSolvedQuizzesQuery(isCorrect = isCorrect)

fun createQuizOption(
    id: UUID? = QUIZ_OPTION_ID,
    quizId: UUID = QUIZ_ID,
    content: String = QUIZ_OPTIONS.first(),
    isAnswer: Boolean = true
): QuizOption =
    QuizOption(
        id = id,
        quizId = quizId,
        content = content,
        isAnswer = isAnswer
    )

fun createQuizOptions(): List<QuizOption> = listOf(createQuizOption())

fun createQuizReaction(
    quizId: UUID = QUIZ_ID,
    userId: UUID = USER_ID,
    reactionType: ReactionType = INITIAL_REACTION_TYPE
): QuizReaction =
    QuizReaction(
        quizId = quizId,
        userId = userId,
        reactionType = reactionType
    )

fun createQuizDetailProjection(
    id: UUID = QUIZ_ID,
    chapterId: UUID = QUIZ_CHAPTER_ID,
    question: String = QUIZ_QUESTION,
    solution: String = QUIZ_SOLUTION,
    correctCount: Long = QUIZ_CORRECT_COUNT,
    incorrectCount: Long = QUIZ_INCORRECT_COUNT,
    selectedOptionId: UUID? = QUIZ_OPTION_ID,
    isCorrect: Boolean? = true
): QuizDetailProjection =
    QuizDetailProjection(
        id = id,
        chapterId = chapterId,
        question = question,
        solution = solution,
        correctCount = correctCount,
        incorrectCount = incorrectCount,
        selectedOptionId = selectedOptionId,
        isCorrect = isCorrect
    )

fun createUnsolvedQuizDetailProjection(): QuizDetailProjection =
    createQuizDetailProjection(
        selectedOptionId = null,
        isCorrect = null
    )

fun createQuizDetailProjections(): List<QuizDetailProjection> = listOf(createQuizDetailProjection())

fun createUnsolvedQuizDetailProjections(): List<QuizDetailProjection> = listOf(createUnsolvedQuizDetailProjection())

fun createSolvedQuizDetailProjection(
    id: UUID = QUIZ_ID,
    chapterId: UUID = QUIZ_CHAPTER_ID,
    question: String = QUIZ_QUESTION,
    solution: String = QUIZ_SOLUTION,
    correctCount: Long = QUIZ_CORRECT_COUNT,
    incorrectCount: Long = QUIZ_INCORRECT_COUNT,
    selectedOptionId: UUID = QUIZ_OPTION_ID,
    isCorrect: Boolean = true
): SolvedQuizDetailProjection =
    SolvedQuizDetailProjection(
        id = id,
        chapterId = chapterId,
        question = question,
        solution = solution,
        correctCount = correctCount,
        incorrectCount = incorrectCount,
        selectedOptionId = selectedOptionId,
        isCorrect = isCorrect
    )

fun createSolvedQuizDetailProjections(): List<SolvedQuizDetailProjection> = listOf(createSolvedQuizDetailProjection())

fun createGetQuizResults(): List<GetQuizResult> = listOf(createGetQuizResult())

fun createEmptySolvedQuizResults(): List<GetSolvedQuizResult> = emptyList()
