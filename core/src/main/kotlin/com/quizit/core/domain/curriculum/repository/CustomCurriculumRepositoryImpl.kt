package com.quizit.core.domain.curriculum.repository

import com.quizit.core.domain.curriculum.dto.projection.CurriculumDetailProjection
import com.quizit.core.global.jooq.tables.references.*
import org.jooq.DSLContext
import org.jooq.impl.DSL.countDistinct
import java.util.*

class CustomCurriculumRepositoryImpl(
    private val dsl: DSLContext
) : CustomCurriculumRepository {
    override fun findCurriculumDetails(userId: UUID): List<CurriculumDetailProjection> {
        val solvedCount = countDistinct(USER_SOLVED_QUIZ.QUIZ_ID).`as`(CurriculumDetailProjection::solvedCount.name)

        return dsl
            .select(
                CURRICULUM.ID,
                CURRICULUM.TITLE,
                CURRICULUM.IMAGE_URL,
                countDistinct(QUIZ.ID).`as`(CurriculumDetailProjection::totalCount.name),
                solvedCount
            )
            .from(CURRICULUM)
            .leftJoin(COURSE)
            .on(COURSE.CURRICULUM_ID.equal(CURRICULUM.ID))
            .leftJoin(CHAPTER)
            .on(CHAPTER.COURSE_ID.equal(COURSE.ID))
            .leftJoin(QUIZ)
            .on(QUIZ.CHAPTER_ID.equal(CHAPTER.ID))
            .leftJoin(USER_SOLVED_QUIZ)
            .on(
                USER_SOLVED_QUIZ.QUIZ_ID.equal(QUIZ.ID)
                    .and(USER_SOLVED_QUIZ.USER_ID.equal(userId))
            )
            .groupBy(CURRICULUM.ID)
            .orderBy(solvedCount.desc())
            .fetchInto(CurriculumDetailProjection::class.java)
    }
}
