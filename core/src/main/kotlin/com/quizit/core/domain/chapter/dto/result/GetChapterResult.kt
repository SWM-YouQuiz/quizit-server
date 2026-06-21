package com.quizit.core.domain.chapter.dto.result

import com.quizit.core.domain.chapter.dto.projection.ChapterDetialProjection
import java.util.*

data class GetChapterResult(
    val id: UUID,
    val courseId: UUID,
    val description: String,
    val document: String,
    val index: Int,
    val totalCount: Int,
    val solvedCount: Int
) {
    companion object {
        fun from(projection: ChapterDetialProjection): GetChapterResult =
            GetChapterResult(
                id = projection.id,
                courseId = projection.courseId,
                description = projection.description,
                document = projection.document,
                index = projection.index,
                totalCount = projection.totalCount,
                solvedCount = projection.solvedCount
            )
    }
}
