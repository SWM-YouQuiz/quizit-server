package com.quizit.api.fixture

import com.quizit.api.domain.curriculum.dto.response.GetCurriculumResponse
import com.quizit.core.fixture.CURRICULUM_ID
import com.quizit.core.fixture.CURRICULUM_IMAGE
import com.quizit.core.fixture.CURRICULUM_TITLE
import java.util.*

fun createGetCurriculumResponse(
    id: UUID = CURRICULUM_ID,
    title: String = CURRICULUM_TITLE,
    image: String = CURRICULUM_IMAGE,
    totalCount: Int = 1,
    solvedCount: Int = 1
): GetCurriculumResponse =
    GetCurriculumResponse(
        id = id,
        title = title,
        image = image,
        totalCount = totalCount,
        solvedCount = solvedCount
    )

fun createGetCurriculumResponses(): List<GetCurriculumResponse> = listOf(createGetCurriculumResponse())
