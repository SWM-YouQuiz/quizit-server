package com.quizit.core.domain.user.service

import com.quizit.core.domain.user.exception.UserNotFoundException
import com.quizit.core.domain.user.repository.UserRepository
import com.quizit.core.fixture.USER_ID
import com.quizit.core.fixture.USER_RANK_CORRECT_COUNT
import com.quizit.core.fixture.createUser
import com.quizit.core.fixture.createUserRankingProjections
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

class UserServiceTest : BehaviorSpec() {
    override fun isolationMode(): IsolationMode = IsolationMode.InstancePerLeaf

    private val userRepository = mockk<UserRepository>()
    private val userService = UserService(userRepository)

    init {
        given("getUserById()는") {
            `when`("사용자를 조회하면") {
                every { userRepository.findUserById(USER_ID) } returns createUser()

                then("사용자 결과를 반환한다.") {
                    val result = userService.getUserById(USER_ID)

                    result.id shouldBe USER_ID
                }
            }

            `when`("사용자가 없으면") {
                every { userRepository.findUserById(USER_ID) } returns null

                then("예외를 던진다.") {
                    shouldThrow<UserNotFoundException> {
                        userService.getUserById(USER_ID)
                    }
                }
            }
        }

        given("getUserRanks()는") {
            every { userRepository.findUserRanks() } returns
                createUserRankingProjections()

            `when`("랭킹 projection을 조회하면") {
                then("랭킹 결과로 변환한다.") {
                    val result = userService.getUserRanks().first()

                    result.correctCount shouldBe USER_RANK_CORRECT_COUNT
                }
            }
        }
    }
}
