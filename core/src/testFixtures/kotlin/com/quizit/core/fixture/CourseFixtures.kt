package com.quizit.core.fixture

import com.quizit.core.domain.course.dto.projection.CourseProjection
import com.quizit.core.domain.course.dto.result.GetCourseResult
import com.quizit.core.domain.course.entity.Course
import java.util.*

val COURSE_ID: UUID = UUID.fromString("0198fb75-7cc9-7d43-b7cc-f05d161d5c40")
val COURSE_CURRICULUM_ID: UUID = UUID.fromString("0198fb75-7cc9-7d43-b7cc-f05d161d5c47")
const val COURSE_TITLE: String = "Spring Boot"
const val COURSE_SERVICE_TOTAL_COUNT: Int = 4
const val COURSE_SERVICE_SOLVED_COUNT: Int = 1
const val COURSE_DETAIL_RESULT_SIZE: Int = 1
const val COURSE_TOTAL_QUIZ_COUNT: Int = 2
const val COURSE_SOLVED_QUIZ_COUNT: Int = 1

fun createCourse(
    id: UUID? = COURSE_ID,
    title: String = COURSE_TITLE,
    curriculumId: UUID = COURSE_CURRICULUM_ID
): Course =
    Course(
        id = id,
        title = title,
        curriculumId = curriculumId
    )

fun createGetCourseResult(
    id: UUID = COURSE_ID,
    title: String = COURSE_TITLE,
    curriculumId: UUID = COURSE_CURRICULUM_ID,
    totalCount: Int = 1,
    solvedCount: Int = 1
): GetCourseResult =
    GetCourseResult(
        id = id,
        title = title,
        curriculumId = curriculumId,
        totalCount = totalCount,
        solvedCount = solvedCount
    )

fun createCourseProjection(
    id: UUID = COURSE_ID,
    curriculumId: UUID = COURSE_CURRICULUM_ID,
    title: String = COURSE_TITLE,
    totalCount: Int = 1,
    solvedCount: Int = 1
): CourseProjection =
    CourseProjection(
        id = id,
        curriculumId = curriculumId,
        title = title,
        totalCount = totalCount,
        solvedCount = solvedCount
    )

fun createCourseProjections(): List<CourseProjection> =
    listOf(
        createCourseProjection(
            totalCount = COURSE_SERVICE_TOTAL_COUNT,
            solvedCount = COURSE_SERVICE_SOLVED_COUNT
        )
    )

fun createGetCourseResults(): List<GetCourseResult> = listOf(createGetCourseResult())
