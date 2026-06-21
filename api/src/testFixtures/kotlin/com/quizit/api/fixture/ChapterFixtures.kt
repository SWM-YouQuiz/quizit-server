package com.quizit.api.fixture

import com.quizit.api.domain.chapter.dto.response.GetChapterResponse
import com.quizit.core.fixture.CHAPTER_COURSE_ID
import com.quizit.core.fixture.CHAPTER_DESCRIPTION
import com.quizit.core.fixture.CHAPTER_DOCUMENT
import com.quizit.core.fixture.CHAPTER_ID
import com.quizit.core.fixture.CHAPTER_INDEX
import java.util.*

fun createGetChapterResponse(
    id: UUID = CHAPTER_ID,
    description: String = CHAPTER_DESCRIPTION,
    document: String = CHAPTER_DOCUMENT,
    courseId: UUID = CHAPTER_COURSE_ID,
    index: Int = CHAPTER_INDEX,
    totalCount: Int = 1,
    solvedCount: Int = 1
): GetChapterResponse =
    GetChapterResponse(
        id = id,
        description = description,
        document = document,
        courseId = courseId,
        index = index,
        totalCount = totalCount,
        solvedCount = solvedCount
    )

fun createGetChapterResponses(): List<GetChapterResponse> = listOf(createGetChapterResponse())
