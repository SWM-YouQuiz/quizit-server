package com.quizit.core.domain.user.dto.projection

data class UserRankProjection(
    val firstName: String,
    val lastName: String,
    val imageUrl: String,
    val correctCount: Int
)
