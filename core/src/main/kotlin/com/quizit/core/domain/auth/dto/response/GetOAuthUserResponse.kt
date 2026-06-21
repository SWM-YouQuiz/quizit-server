package com.quizit.core.domain.auth.dto.response

import com.quizit.core.domain.user.entity.SocialProvider

data class GetOAuthUserResponse(
    val id: String,
    val socialProvider: SocialProvider,
    val email: String,
    val firstName: String,
    val lastName: String,
    val imageUrl: String
)
