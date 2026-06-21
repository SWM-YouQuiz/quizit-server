package com.quizit.api.domain.user.dto.response

import com.quizit.core.domain.user.dto.result.GetUserResult
import com.quizit.core.domain.user.entity.Role
import com.quizit.core.domain.user.entity.SocialProvider
import java.util.*

data class GetUserResponse(
    val id: UUID,
    val email: String,
    val socialProvider: SocialProvider,
    val firstName: String,
    val lastName: String,
    val imageUrl: String,
    val answerRate: Double,
    val role: Role
) {
    companion object {
        fun from(result: GetUserResult): GetUserResponse =
            with(result) {
                GetUserResponse(
                    id = id,
                    email = email,
                    socialProvider = socialProvider,
                    firstName = firstName,
                    lastName = lastName,
                    imageUrl = imageUrl,
                    answerRate = answerRate,
                    role = role
                )
            }
    }
}
