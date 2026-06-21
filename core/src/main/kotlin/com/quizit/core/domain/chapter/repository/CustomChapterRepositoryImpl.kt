package com.quizit.core.domain.chapter.repository

import com.quizit.core.domain.chapter.dto.projection.ChapterDetialProjection
import com.quizit.core.global.jooq.tables.references.CHAPTER
import com.quizit.core.global.jooq.tables.references.QUIZ
import com.quizit.core.global.jooq.tables.references.USER_SOLVED_QUIZ
import org.jooq.DSLContext
import org.jooq.impl.DSL.countDistinct
import java.util.*

class CustomChapterRepositoryImpl(
    private val dsl: DSLContext
) : CustomChapterRepository {
    override fun findChapterDetails(
        courseId: UUID,
        userId: UUID
    ): List<ChapterDetialProjection> =
        dsl
            .select(
                CHAPTER.ID,
                CHAPTER.COURSE_ID,
                CHAPTER.DESCRIPTION,
                CHAPTER.DOCUMENT,
                CHAPTER.INDEX,
                countDistinct(QUIZ.ID).`as`(ChapterDetialProjection::totalCount.name),
                countDistinct(USER_SOLVED_QUIZ.QUIZ_ID).`as`(ChapterDetialProjection::solvedCount.name)
            )
            .from(CHAPTER)
            .leftJoin(QUIZ)
            .on(QUIZ.CHAPTER_ID.equal(CHAPTER.ID))
            .leftJoin(USER_SOLVED_QUIZ)
            .on(
                USER_SOLVED_QUIZ.QUIZ_ID.equal(QUIZ.ID)
                    .and(USER_SOLVED_QUIZ.USER_ID.equal(userId))
            )
            .where(CHAPTER.COURSE_ID.equal(courseId))
            .groupBy(CHAPTER.ID)
            .orderBy(CHAPTER.INDEX.asc())
            .fetchInto(ChapterDetialProjection::class.java)
}
