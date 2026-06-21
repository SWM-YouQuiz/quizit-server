package com.quizit.core.domain.chapter.repository

import com.quizit.core.domain.chapter.dto.projection.ChapterDetialProjection
import java.util.*

interface CustomChapterRepository {
    fun findChapterDetails(
        courseId: UUID,
        userId: UUID
    ): List<ChapterDetialProjection>
}
