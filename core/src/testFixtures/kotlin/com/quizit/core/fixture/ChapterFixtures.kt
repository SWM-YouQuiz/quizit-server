package com.quizit.core.fixture

import com.quizit.core.domain.chapter.dto.projection.ChapterDetialProjection
import com.quizit.core.domain.chapter.dto.result.GetChapterResult
import com.quizit.core.domain.chapter.entity.Chapter
import java.util.UUID

val CHAPTER_ID: UUID = UUID.fromString("0198fb75-7cc9-7d43-b7cc-f05d161d5c41")
val CHAPTER_COURSE_ID: UUID = UUID.fromString("0198fb75-7cc9-7d43-b7cc-f05d161d5c43")
const val CHAPTER_DESCRIPTION: String = "Spring Boot 시작하기"
const val CHAPTER_DOCUMENT: String = "Spring Boot 소개 문서"
const val CHAPTER_INDEX: Int = 1
const val CHAPTER_SERVICE_TOTAL_COUNT: Int = 3
const val CHAPTER_SERVICE_SOLVED_COUNT: Int = 2
const val CHAPTER_DETAIL_RESULT_SIZE: Int = 1
const val CHAPTER_TOTAL_QUIZ_COUNT: Int = 2
const val CHAPTER_SOLVED_QUIZ_COUNT: Int = 1

fun createChapter(
    id: UUID? = CHAPTER_ID,
    description: String = CHAPTER_DESCRIPTION,
    document: String = CHAPTER_DOCUMENT,
    courseId: UUID = CHAPTER_COURSE_ID,
    index: Int = CHAPTER_INDEX
): Chapter =
    Chapter(
        id = id,
        description = description,
        document = document,
        courseId = courseId,
        index = index
    )

fun createGetChapterResult(
    id: UUID = CHAPTER_ID,
    description: String = CHAPTER_DESCRIPTION,
    document: String = CHAPTER_DOCUMENT,
    courseId: UUID = CHAPTER_COURSE_ID,
    index: Int = CHAPTER_INDEX,
    totalCount: Int = 1,
    solvedCount: Int = 1
): GetChapterResult =
    GetChapterResult(
        id = id,
        description = description,
        document = document,
        courseId = courseId,
        index = index,
        totalCount = totalCount,
        solvedCount = solvedCount
    )

fun createChapterDetailProjection(
    id: UUID = CHAPTER_ID,
    courseId: UUID = CHAPTER_COURSE_ID,
    description: String = CHAPTER_DESCRIPTION,
    document: String = CHAPTER_DOCUMENT,
    index: Int = CHAPTER_INDEX,
    totalCount: Int = 1,
    solvedCount: Int = 1
): ChapterDetialProjection =
    ChapterDetialProjection(
        id = id,
        courseId = courseId,
        description = description,
        document = document,
        index = index,
        totalCount = totalCount,
        solvedCount = solvedCount
    )

fun createChapterDetailProjections(): List<ChapterDetialProjection> =
    listOf(
        createChapterDetailProjection(
            totalCount = CHAPTER_SERVICE_TOTAL_COUNT,
            solvedCount = CHAPTER_SERVICE_SOLVED_COUNT
        )
    )

fun createGetChapterResults(): List<GetChapterResult> = listOf(createGetChapterResult())
