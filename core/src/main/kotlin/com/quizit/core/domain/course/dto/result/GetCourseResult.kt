package com.quizit.core.domain.course.dto.result

import com.quizit.core.domain.course.dto.projection.CourseProjection
import java.util.*

data class GetCourseResult(
    val id: UUID,
    val curriculumId: UUID,
    val title: String,
    val totalCount: Int,
    val solvedCount: Int
) {
    companion object {
        fun from(projection: CourseProjection): GetCourseResult =
            GetCourseResult(
                id = projection.id,
                curriculumId = projection.curriculumId,
                title = projection.title,
                totalCount = projection.totalCount,
                solvedCount = projection.solvedCount
            )
    }
}
