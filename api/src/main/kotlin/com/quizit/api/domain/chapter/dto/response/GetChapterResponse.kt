package com.quizit.api.domain.chapter.dto.response

import com.quizit.core.domain.chapter.dto.result.GetChapterResult
import java.util.*

data class GetChapterResponse(
    val id: UUID,
    val description: String,
    val document: String,
    val courseId: UUID,
    val index: Int,
    val totalCount: Int,
    val solvedCount: Int
) {
    companion object {
        fun from(result: GetChapterResult): GetChapterResponse =
            with(result) {
                GetChapterResponse(
                    id = id,
                    description = description,
                    document = document,
                    courseId = courseId,
                    index = index,
                    totalCount = totalCount,
                    solvedCount = solvedCount
                )
            }
    }
}
