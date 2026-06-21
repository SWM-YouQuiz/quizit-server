package com.quizit.core.domain.user.repository

import com.quizit.core.domain.user.dto.projection.UserRankProjection

interface CustomUserRepository {
    fun findUserRanks(): List<UserRankProjection>
}
