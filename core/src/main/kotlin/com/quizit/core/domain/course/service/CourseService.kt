package com.quizit.core.domain.course.service

import com.quizit.core.domain.course.dto.result.GetCourseResult
import com.quizit.core.domain.course.repository.CourseRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class CourseService(
    private val courseRepository: CourseRepository
) {
    @Transactional(readOnly = true)
    fun getCoursesByCurriculumId(
        curriculumId: UUID,
        userId: UUID
    ): List<GetCourseResult> =
        courseRepository.findAllByCurriculumId(curriculumId = curriculumId, userId = userId)
            .map { GetCourseResult.from(it) }
}
