package com.quizit.core.domain.chapter.dto.projection

import java.util.*

data class ChapterDetialProjection(
    val id: UUID,
    val courseId: UUID,
    val description: String,
    val document: String,
    val index: Int,
    val totalCount: Int,
    val solvedCount: Int
)
