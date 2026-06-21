package com.quizit.api.domain.user.controller

import com.quizit.api.domain.user.dto.response.GetUserRankResponse
import com.quizit.api.domain.user.dto.response.GetUserResponse
import com.quizit.api.global.annotation.AuthenticationId
import com.quizit.core.domain.user.service.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/api/v1")
class UserController(
    private val userService: UserService
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
}
