package com.quizit.api.domain.auth.controller

import com.quizit.api.domain.auth.dto.request.LoginRequest
import com.quizit.api.domain.auth.dto.request.RefreshRequest
import com.quizit.api.domain.auth.dto.response.LoginResponse
import com.quizit.api.domain.auth.dto.response.RefreshResponse
import com.quizit.api.global.annotation.AuthenticationId
import com.quizit.core.domain.auth.service.AuthService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val authService: AuthService
) {
    @PostMapping("/login")
    fun login(
        @Valid
        @RequestBody
        request: LoginRequest
    ): LoginResponse = LoginResponse.from(authService.login(request.toCommand()))

    @PostMapping("/refresh")
    fun refresh(
        @Valid
        @RequestBody
        request: RefreshRequest
    ): RefreshResponse = RefreshResponse.from(authService.refresh(request.toCommand()))

    @PostMapping("/logout")
    fun logout(
        @AuthenticationId
        userId: UUID
    ) {
        authService.logout(userId)
    }
}
