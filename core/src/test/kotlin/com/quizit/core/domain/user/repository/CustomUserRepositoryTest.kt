package com.quizit.core.domain.user.repository

import com.quizit.core.common.RepositoryTest
import com.quizit.core.fixture.RANKED_USER_SOCIAL_ID
import com.quizit.core.fixture.UNRANKED_USER_SOCIAL_ID
import com.quizit.core.fixture.USER_CORRECT_QUIZ_COUNT
import com.quizit.core.fixture.USER_EMPTY_CORRECT_QUIZ_COUNT
import com.quizit.core.fixture.USER_INCORRECT_QUIZ_FILTER
import com.quizit.core.fixture.USER_RANK_RESULT_SIZE
import com.quizit.core.fixture.USER_UNSOLVED_QUIZ_ID
import com.quizit.core.fixture.createUser
import com.quizit.core.fixture.createUserSolvedQuiz
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired

class CustomUserRepositoryTest : RepositoryTest() {
    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var userSolvedQuizRepository: UserSolvedQuizRepository

    init {
        "findUserRanks()는 사용자별 정답 수를 조회한다." {
            val user = userRepository.save(createUser(id = null, socialId = RANKED_USER_SOCIAL_ID))
            userRepository.save(createUser(id = null, socialId = UNRANKED_USER_SOCIAL_ID))
            userSolvedQuizRepository.save(createUserSolvedQuiz(id = null, userId = user.id!!))
            userSolvedQuizRepository.save(
                createUserSolvedQuiz(
                    id = null,
                    userId = user.id!!,
                    quizId = USER_UNSOLVED_QUIZ_ID,
                    isCorrect = USER_INCORRECT_QUIZ_FILTER
                )
            )

            val results = userRepository.findUserRanks()

            results shouldHaveSize USER_RANK_RESULT_SIZE
            results.first().correctCount shouldBe USER_CORRECT_QUIZ_COUNT
            results.last().correctCount shouldBe USER_EMPTY_CORRECT_QUIZ_COUNT
        }
    }
}
