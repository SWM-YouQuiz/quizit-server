package com.quizit.api.domain.course.controller

import com.quizit.api.domain.course.dto.response.GetCourseResponse
import com.quizit.api.global.annotation.AuthenticationId
import com.quizit.core.domain.course.service.CourseService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*
import kotlin.collections.map

@RestController
@RequestMapping("/api/v1")
class CourseController(
    private val courseService: CourseService
) {
    @GetMapping("/curriculums/{curriculum_id}/courses")
    fun getCoursesByCurriculumId(
        @AuthenticationId
        userId: UUID,
        @PathVariable("curriculum_id")
        curriculumId: UUID
    ): List<GetCourseResponse> =
        courseService.getCoursesByCurriculumId(curriculumId = curriculumId, userId = userId)
            .map { GetCourseResponse.from(it) }
}
