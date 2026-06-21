package com.quizit.core.domain.user.dto.result

import com.quizit.core.domain.user.entity.Role
import com.quizit.core.domain.user.entity.SocialProvider
import com.quizit.core.domain.user.entity.User
import java.util.*

data class GetUserResult(
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
        fun from(user: User): GetUserResult =
            GetUserResult(
                id = user.id!!,
                email = user.email,
                socialProvider = user.socialProvider,
                firstName = user.firstName,
                lastName = user.lastName,
                imageUrl = user.imageUrl,
                answerRate = 0.0,
                role = user.role
            )
    }
}
