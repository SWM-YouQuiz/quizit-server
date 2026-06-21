package com.quizit.core.domain.chapter.service

import com.quizit.core.domain.chapter.dto.result.GetChapterResult
import com.quizit.core.domain.chapter.repository.ChapterRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class ChapterService(
    private val chapterRepository: ChapterRepository
) {
    @Transactional(readOnly = true)
    fun getChaptersByCourseId(
        courseId: UUID,
        userId: UUID
    ): List<GetChapterResult> =
        chapterRepository.findChapterDetails(courseId = courseId, userId = userId)
            .map { GetChapterResult.from(it) }
}
