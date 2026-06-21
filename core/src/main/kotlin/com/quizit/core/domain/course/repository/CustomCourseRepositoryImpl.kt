package com.quizit.core.domain.course.repository

import com.quizit.core.domain.course.dto.projection.CourseProjection
import com.quizit.core.global.jooq.tables.references.CHAPTER
import com.quizit.core.global.jooq.tables.references.COURSE
import com.quizit.core.global.jooq.tables.references.QUIZ
import com.quizit.core.global.jooq.tables.references.USER_SOLVED_QUIZ
import org.jooq.DSLContext
import org.jooq.impl.DSL.countDistinct
import java.util.*

class CustomCourseRepositoryImpl(
    private val dsl: DSLContext
) : CustomCourseRepository {
    override fun findAllByCurriculumId(
        curriculumId: UUID,
        userId: UUID
    ): List<CourseProjection> {
        val solvedCount = countDistinct(USER_SOLVED_QUIZ.QUIZ_ID).`as`(CourseProjection::solvedCount.name)

        return dsl
            .select(
                COURSE.ID,
                COURSE.CURRICULUM_ID,
                COURSE.TITLE,
                countDistinct(QUIZ.ID).`as`(CourseProjection::totalCount.name),
                solvedCount
            )
            .from(COURSE)
            .leftJoin(CHAPTER)
            .on(CHAPTER.COURSE_ID.equal(COURSE.ID))
            .leftJoin(QUIZ)
            .on(QUIZ.CHAPTER_ID.equal(CHAPTER.ID))
            .leftJoin(USER_SOLVED_QUIZ)
            .on(
                USER_SOLVED_QUIZ.QUIZ_ID.equal(QUIZ.ID)
                    .and(USER_SOLVED_QUIZ.USER_ID.equal(userId))
            )
            .where(COURSE.CURRICULUM_ID.equal(curriculumId))
            .groupBy(COURSE.ID)
            .orderBy(solvedCount.desc())
            .fetchInto(CourseProjection::class.java)
    }
}
