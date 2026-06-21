package com.quizit.api.domain.chapter.controller

import com.quizit.api.domain.chapter.dto.response.GetChapterResponse
import com.quizit.api.global.annotation.AuthenticationId
import com.quizit.core.domain.chapter.service.ChapterService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*
import kotlin.collections.map

@RestController
@RequestMapping("/api/v1")
class ChapterController(
    private val chapterService: ChapterService
) {
    @GetMapping("/courses/{course_id}/chapters")
    fun getChaptersByCourseId(
        @AuthenticationId
        userId: UUID,
        @PathVariable("course_id")
        courseId: UUID
    ): List<GetChapterResponse> =
        chapterService.getChaptersByCourseId(courseId = courseId, userId = userId)
            .map { GetChapterResponse.from(it) }
}
