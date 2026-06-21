package com.quizit.core.domain.user.repository

import com.quizit.core.domain.user.entity.SocialProvider
import com.quizit.core.domain.user.entity.User
import com.quizit.core.global.repository.JdbcRepository
import java.util.UUID

interface UserRepository :
    JdbcRepository<User, UUID>,
    CustomUserRepository {
    fun findUserById(id: UUID): User?

    fun findBySocialIdAndSocialProvider(
        socialId: String,
        socialProvider: SocialProvider
    ): User?
}
