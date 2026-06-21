package com.quizit.core.domain.course.repository

import com.quizit.core.domain.course.dto.projection.CourseProjection
import java.util.UUID

interface CustomCourseRepository {
    fun findAllByCurriculumId(
        curriculumId: UUID,
        userId: UUID
    ): List<CourseProjection>
}
