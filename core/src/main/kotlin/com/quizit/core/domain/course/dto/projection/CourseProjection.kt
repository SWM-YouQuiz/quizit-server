package com.quizit.core.domain.course.dto.projection

import java.util.*

data class CourseProjection(
    val id: UUID,
    val curriculumId: UUID,
    val title: String,
    val totalCount: Int,
    val solvedCount: Int
)
