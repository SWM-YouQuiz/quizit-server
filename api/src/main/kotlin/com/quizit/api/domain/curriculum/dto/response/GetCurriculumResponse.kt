package com.quizit.api.domain.curriculum.dto.response

import com.quizit.core.domain.curriculum.dto.result.GetCurriculumResult
import java.util.*

data class GetCurriculumResponse(
    val id: UUID,
    val title: String,
    val image: String,
    val totalCount: Int,
    val solvedCount: Int
) {
    companion object {
        fun from(result: GetCurriculumResult): GetCurriculumResponse =
            with(result) {
                GetCurriculumResponse(
                    id = id,
                    title = title,
                    image = image,
                    totalCount = totalCount,
                    solvedCount = solvedCount
                )
            }
    }
}
