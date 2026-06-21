package com.quizit.core.domain.user.repository

import com.quizit.core.domain.user.dto.projection.UserRankProjection
import com.quizit.core.global.jooq.tables.references.USER
import com.quizit.core.global.jooq.tables.references.USER_SOLVED_QUIZ
import org.jooq.DSLContext
import org.jooq.impl.DSL.countDistinct

class CustomUserRepositoryImpl(
    private val dsl: DSLContext
) : CustomUserRepository {
    override fun findUserRanks(): List<UserRankProjection> {
        val correctCount = countDistinct(USER_SOLVED_QUIZ.QUIZ_ID).`as`(UserRankProjection::correctCount.name)

        return dsl
            .select(
                USER.FIRST_NAME,
                USER.LAST_NAME,
                USER.IMAGE_URL,
                correctCount
            )
            .from(USER)
            .leftJoin(USER_SOLVED_QUIZ)
            .on(
                USER_SOLVED_QUIZ.USER_ID.equal(USER.ID)
                    .and(USER_SOLVED_QUIZ.IS_CORRECT.equal(true))
            )
            .groupBy(USER.ID)
            .orderBy(correctCount.desc())
            .fetchInto(UserRankProjection::class.java)
    }
}
