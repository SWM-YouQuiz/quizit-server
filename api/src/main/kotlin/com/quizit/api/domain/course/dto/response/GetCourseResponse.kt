package com.quizit.api.domain.course.dto.response

import com.quizit.core.domain.course.dto.result.GetCourseResult
import java.util.*

data class GetCourseResponse(
    val id: UUID,
    val title: String,
    val curriculumId: UUID,
    val totalCount: Int,
    val solvedCount: Int
) {
    companion object {
        fun from(result: GetCourseResult): GetCourseResponse =
            with(result) {
                GetCourseResponse(
                    id = id,
                    title = title,
                    curriculumId = curriculumId,
                    totalCount = totalCount,
                    solvedCount = solvedCount
                )
            }
    }
}
