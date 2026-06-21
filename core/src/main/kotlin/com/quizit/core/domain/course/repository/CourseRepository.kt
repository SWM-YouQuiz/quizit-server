package com.quizit.core.domain.course.repository

import com.quizit.core.domain.course.entity.Course
import com.quizit.core.global.repository.JdbcRepository
import java.util.UUID

interface CourseRepository :
    JdbcRepository<Course, UUID>,
    CustomCourseRepository
