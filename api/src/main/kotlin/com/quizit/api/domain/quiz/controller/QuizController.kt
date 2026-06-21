package com.quizit.api.domain.quiz.controller

import com.quizit.api.domain.quiz.dto.response.GetQuizResponse
import com.quizit.api.global.annotation.AuthenticationId
import com.quizit.core.domain.quiz.service.QuizService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/api/v1")
class QuizController(
    private val quizService: QuizService
) {
    @GetMapping("/chapters/{chapter_id}/quizzes")
    fun getQuizzesByChapterId(
        @AuthenticationId
        userId: UUID,
        @PathVariable("chapter_id")
        chapterId: UUID
    ): List<GetQuizResponse> =
        quizService.getQuizzesByChapterId(chapterId = chapterId, userId = userId)
            .map { GetQuizResponse.from(it) }
}
