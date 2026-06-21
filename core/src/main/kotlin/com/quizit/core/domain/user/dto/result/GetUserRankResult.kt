package com.quizit.core.domain.user.dto.result

import com.quizit.core.domain.user.dto.projection.UserRankProjection

data class GetUserRankResult(
    val firstName: String,
    val lastName: String,
    val imageUrl: String,
    val level: Int,
    val correctCount: Int
) {
    companion object {
        fun from(projection: UserRankProjection): GetUserRankResult =
            with(projection) {
                GetUserRankResult(
                    firstName = firstName,
                    lastName = lastName,
                    imageUrl = imageUrl,
                    level = 1,
                    correctCount = correctCount
                )
            }
    }
}
