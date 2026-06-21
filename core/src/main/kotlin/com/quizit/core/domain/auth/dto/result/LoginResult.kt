package com.quizit.core.domain.auth.dto.result

data class LoginResult(
    val accessToken: String,
    val refreshToken: String
)
