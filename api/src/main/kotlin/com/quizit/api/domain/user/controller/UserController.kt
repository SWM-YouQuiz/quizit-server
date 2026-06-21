package com.quizit.api.domain.user.controller

import com.quizit.api.domain.quiz.dto.response.GetSolvedQuizResponse
import com.quizit.api.domain.user.dto.response.GetUserRankResponse
import com.quizit.api.domain.user.dto.response.GetUserResponse
import com.quizit.api.global.annotation.AuthenticationId
import com.quizit.core.domain.quiz.dto.query.GetSolvedQuizzesQuery
import com.quizit.core.domain.quiz.service.QuizService
import com.quizit.core.domain.user.service.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/api/v1")
class UserController(
    private val userService: UserService,
    private val quizService: QuizService
) {
    @GetMapping("/users/me")
    fun getMe(
        @AuthenticationId
        userId: UUID
    ): GetUserResponse = GetUserResponse.from(userService.getUserById(userId))

    @GetMapping("/users/me/rankings")
    fun getRanking(): List<GetUserRankResponse> =
        userService.getUserRanks()
            .map { GetUserRankResponse.from(it) }

    @GetMapping("/users/me/solved-quizzes")
    fun getSolvedQuizzes(
        @AuthenticationId
        userId: UUID,
        @RequestParam(required = false)
        isCorrect: Boolean?
    ): List<GetSolvedQuizResponse> =
        quizService.getSolvedQuizzes(userId, GetSolvedQuizzesQuery(isCorrect = isCorrect))
            .map { GetSolvedQuizResponse.from(it) }
}
