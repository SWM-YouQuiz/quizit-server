package com.quizit.api.fixture

import com.quizit.api.domain.course.dto.response.GetCourseResponse
import com.quizit.core.fixture.COURSE_CURRICULUM_ID
import com.quizit.core.fixture.COURSE_ID
import com.quizit.core.fixture.COURSE_TITLE
import java.util.*

fun createGetCourseResponse(
    id: UUID = COURSE_ID,
    title: String = COURSE_TITLE,
    curriculumId: UUID = COURSE_CURRICULUM_ID,
    totalCount: Int = 1,
    solvedCount: Int = 1
): GetCourseResponse =
    GetCourseResponse(
        id = id,
        title = title,
        curriculumId = curriculumId,
        totalCount = totalCount,
        solvedCount = solvedCount
    )

fun createGetCourseResponses(): List<GetCourseResponse> = listOf(createGetCourseResponse())
