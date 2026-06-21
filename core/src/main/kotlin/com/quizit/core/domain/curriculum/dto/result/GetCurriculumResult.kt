package com.quizit.core.domain.curriculum.dto.result

import com.quizit.core.domain.curriculum.dto.projection.CurriculumDetailProjection
import java.util.*

data class GetCurriculumResult(
    val id: UUID,
    val title: String,
    val image: String,
    val totalCount: Int,
    val solvedCount: Int
) {
    companion object {
        fun from(projection: CurriculumDetailProjection): GetCurriculumResult =
            GetCurriculumResult(
                id = projection.id,
                title = projection.title,
                image = projection.imageUrl,
                totalCount = projection.totalCount,
                solvedCount = projection.solvedCount
            )
    }
}
