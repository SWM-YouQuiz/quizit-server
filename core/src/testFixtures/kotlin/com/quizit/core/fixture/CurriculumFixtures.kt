package com.quizit.core.fixture

import com.quizit.core.domain.curriculum.dto.projection.CurriculumDetailProjection
import com.quizit.core.domain.curriculum.dto.result.GetCurriculumResult
import com.quizit.core.domain.curriculum.entity.Curriculum
import java.util.*

val CURRICULUM_ID: UUID = UUID.fromString("0198fb75-7cc9-7d43-b7cc-f05d161d5c3f")
const val CURRICULUM_TITLE: String = "Kotlin"
const val CURRICULUM_IMAGE: String = "https://example.com/kotlin.png"
const val CURRICULUM_SERVICE_TOTAL_COUNT: Int = 5
const val CURRICULUM_SERVICE_SOLVED_COUNT: Int = 3
const val CURRICULUM_DETAIL_RESULT_SIZE: Int = 1
const val CURRICULUM_TOTAL_QUIZ_COUNT: Int = 2
const val CURRICULUM_SOLVED_QUIZ_COUNT: Int = 1

fun createCurriculum(
    id: UUID? = CURRICULUM_ID,
    title: String = CURRICULUM_TITLE,
    image: String = CURRICULUM_IMAGE
): Curriculum =
    Curriculum(
        id = id,
        title = title,
        imageUrl = image
    )

fun createGetCurriculumResult(
    id: UUID = CURRICULUM_ID,
    title: String = CURRICULUM_TITLE,
    image: String = CURRICULUM_IMAGE,
    totalCount: Int = 1,
    solvedCount: Int = 1
): GetCurriculumResult =
    GetCurriculumResult(
        id = id,
        title = title,
        image = image,
        totalCount = totalCount,
        solvedCount = solvedCount
    )

fun createCurriculumDetailProjection(
    id: UUID = CURRICULUM_ID,
    title: String = CURRICULUM_TITLE,
    imageUrl: String = CURRICULUM_IMAGE,
    totalCount: Int = 1,
    solvedCount: Int = 1
): CurriculumDetailProjection =
    CurriculumDetailProjection(
        id = id,
        title = title,
        imageUrl = imageUrl,
        totalCount = totalCount,
        solvedCount = solvedCount
    )

fun createCurriculumDetailProjections(): List<CurriculumDetailProjection> =
    listOf(
        createCurriculumDetailProjection(
            totalCount = CURRICULUM_SERVICE_TOTAL_COUNT,
            solvedCount = CURRICULUM_SERVICE_SOLVED_COUNT
        )
    )

fun createGetCurriculumResults(): List<GetCurriculumResult> = listOf(createGetCurriculumResult())
