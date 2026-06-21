package com.quizit.core.domain.user.service

import com.quizit.core.domain.user.dto.result.GetUserRankResult
import com.quizit.core.domain.user.dto.result.GetUserResult
import com.quizit.core.domain.user.exception.UserNotFoundException
import com.quizit.core.domain.user.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class UserService(
    private val userRepository: UserRepository
) {
    @Transactional(readOnly = true)
    fun getUserById(id: UUID): GetUserResult =
        userRepository.findUserById(id)
            ?.let { GetUserResult.from(it) }
            ?: throw UserNotFoundException()

    @Transactional(readOnly = true)
    fun getUserRanks(): List<GetUserRankResult> =
        userRepository.findUserRanks()
            .map { GetUserRankResult.from(it) }
}
