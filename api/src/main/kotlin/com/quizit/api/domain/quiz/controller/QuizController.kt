package com.quizit.api.domain.quiz.controller

import com.quizit.api.domain.quiz.dto.request.GradeQuizRequest
import com.quizit.api.domain.quiz.dto.response.GetQuizResponse
import com.quizit.api.domain.quiz.dto.response.GradeQuizResponse
import com.quizit.api.global.annotation.AuthenticationId
import com.quizit.core.domain.quiz.service.QuizService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/api/v1")
class QuizController(
    private val quizService: QuizService
) {
    @PostMapping("/users/me/quizzes/{quiz_id}/grade")
    fun gradeQuiz(
        @AuthenticationId
        userId: UUID,
        @PathVariable("quiz_id")
        quizId: UUID,
        @RequestBody
        request: GradeQuizRequest
    ): GradeQuizResponse =
        GradeQuizResponse.from(
            quizService.gradeQuiz(
                userId = userId,
                command = request.toCommand(quizId)
            )
        )

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
