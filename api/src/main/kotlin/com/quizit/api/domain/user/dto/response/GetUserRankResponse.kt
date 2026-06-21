package com.quizit.api.domain.user.dto.response

import com.quizit.core.domain.user.dto.result.GetUserRankResult

data class GetUserRankResponse(
    val firstName: String,
    val lastName: String,
    val imageUrl: String,
    val level: Int,
    val correctCount: Int
) {
    companion object {
        fun from(result: GetUserRankResult): GetUserRankResponse =
            with(result) {
                GetUserRankResponse(
                    firstName = firstName,
                    lastName = lastName,
                    imageUrl = imageUrl,
                    level = level,
                    correctCount = correctCount
                )
            }
    }
}
