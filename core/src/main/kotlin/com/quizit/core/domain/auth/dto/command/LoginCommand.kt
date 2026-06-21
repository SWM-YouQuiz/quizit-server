package com.quizit.core.domain.auth.dto.command

import com.quizit.core.domain.user.entity.SocialProvider

data class LoginCommand(
    val oAuthToken: String,
    val provider: SocialProvider
)
