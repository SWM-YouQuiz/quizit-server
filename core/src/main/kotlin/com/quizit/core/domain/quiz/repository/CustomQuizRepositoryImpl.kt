package com.quizit.core.domain.quiz.repository

import com.quizit.core.domain.quiz.dto.projection.QuizDetailProjection
import com.quizit.core.domain.quiz.dto.projection.SolvedQuizDetailProjection
import com.quizit.core.global.jooq.tables.references.QUIZ
import com.quizit.core.global.jooq.tables.references.USER_SOLVED_QUIZ
import org.jooq.DSLContext
import org.jooq.impl.DSL.*
import java.util.*

class CustomQuizRepositoryImpl(
    private val dsl: DSLContext
) : CustomQuizRepository {
    override fun findQuizDetails(
        userId: UUID,
        chapterId: UUID
    ): List<QuizDetailProjection> {
        val quizStatistics =
            dsl
                .select(
                    USER_SOLVED_QUIZ.QUIZ_ID,
                    count().filterWhere(USER_SOLVED_QUIZ.IS_CORRECT.equal(true))
                        .`as`(QuizDetailProjection::correctCount.name),
                    count().filterWhere(USER_SOLVED_QUIZ.IS_CORRECT.equal(false))
                        .`as`(QuizDetailProjection::incorrectCount.name)
                )
                .from(USER_SOLVED_QUIZ)
                .groupBy(USER_SOLVED_QUIZ.QUIZ_ID)

        return dsl
            .select(
                QUIZ.ID,
                QUIZ.CHAPTER_ID,
                QUIZ.QUESTION,
                QUIZ.SOLUTION,
                coalesce(quizStatistics.field(QuizDetailProjection::correctCount.name, Long::class.java), 0L)
                    .`as`(QuizDetailProjection::correctCount.name),
                coalesce(quizStatistics.field(QuizDetailProjection::incorrectCount.name, Long::class.java), 0L)
                    .`as`(QuizDetailProjection::incorrectCount.name),
                USER_SOLVED_QUIZ.SELECTED_OPTION_ID,
                USER_SOLVED_QUIZ.IS_CORRECT
            )
            .from(QUIZ)
            .leftJoin(quizStatistics)
            .on(quizStatistics.field(USER_SOLVED_QUIZ.QUIZ_ID)?.equal(QUIZ.ID))
            .leftJoin(USER_SOLVED_QUIZ)
            .on(
                USER_SOLVED_QUIZ.QUIZ_ID.equal(QUIZ.ID)
                    .and(USER_SOLVED_QUIZ.USER_ID.equal(userId))
            )
            .where(QUIZ.CHAPTER_ID.equal(chapterId))
            .fetchInto(QuizDetailProjection::class.java)
    }

    override fun findSolvedQuizDetails(
        userId: UUID,
        isCorrect: Boolean?
    ): List<SolvedQuizDetailProjection> {
        val quizStatistics =
            dsl
                .select(
                    USER_SOLVED_QUIZ.QUIZ_ID,
                    count().filterWhere(USER_SOLVED_QUIZ.IS_CORRECT.equal(true))
                        .`as`(QuizDetailProjection::correctCount.name),
                    count().filterWhere(USER_SOLVED_QUIZ.IS_CORRECT.equal(false))
                        .`as`(QuizDetailProjection::incorrectCount.name)
                )
                .from(USER_SOLVED_QUIZ)
                .groupBy(USER_SOLVED_QUIZ.QUIZ_ID)

        return dsl
            .select(
                QUIZ.ID,
                QUIZ.CHAPTER_ID,
                QUIZ.QUESTION,
                QUIZ.SOLUTION,
                coalesce(quizStatistics.field(QuizDetailProjection::correctCount.name, Long::class.java), 0L)
                    .`as`(QuizDetailProjection::correctCount.name),
                coalesce(quizStatistics.field(QuizDetailProjection::incorrectCount.name, Long::class.java), 0L)
                    .`as`(QuizDetailProjection::incorrectCount.name),
                USER_SOLVED_QUIZ.SELECTED_OPTION_ID,
                USER_SOLVED_QUIZ.IS_CORRECT
            )
            .from(USER_SOLVED_QUIZ)
            .join(QUIZ)
            .on(QUIZ.ID.equal(USER_SOLVED_QUIZ.QUIZ_ID))
            .leftJoin(quizStatistics)
            .on(quizStatistics.field(USER_SOLVED_QUIZ.QUIZ_ID)?.equal(QUIZ.ID))
            .where(
                USER_SOLVED_QUIZ.USER_ID.equal(userId)
                    .and(if (isCorrect == null) noCondition() else USER_SOLVED_QUIZ.IS_CORRECT.equal(isCorrect))
            )
            .fetchInto(SolvedQuizDetailProjection::class.java)
    }
}
