package com.quizit.core.domain.curriculum.dto.projection

import java.util.*

data class CurriculumDetailProjection(
    val id: UUID,
    val title: String,
    val imageUrl: String,
    val totalCount: Int,
    val solvedCount: Int
)
