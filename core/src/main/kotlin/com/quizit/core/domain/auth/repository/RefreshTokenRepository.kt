package com.quizit.core.domain.auth.repository

import com.quizit.core.domain.auth.entity.RefreshToken
import com.quizit.core.global.repository.RedisRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface RefreshTokenRepository : RedisRepository<RefreshToken, UUID> {
    fun findByUserId(userId: UUID): RefreshToken?

    fun deleteByUserId(userId: UUID): Int
}
