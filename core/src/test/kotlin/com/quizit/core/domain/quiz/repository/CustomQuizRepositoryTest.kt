package com.quizit.core.domain.quiz.repository

import com.quizit.core.common.RepositoryTest
import com.quizit.core.domain.user.repository.UserSolvedQuizRepository
import com.quizit.core.fixture.INCORRECT_QUIZ_FILTER
import com.quizit.core.fixture.OTHER_USER_ID
import com.quizit.core.fixture.QUIZ_CORRECT_COUNT
import com.quizit.core.fixture.QUIZ_DETAIL_RESULT_SIZE
import com.quizit.core.fixture.QUIZ_OPTION_ID
import com.quizit.core.fixture.QUIZ_OTHER_INCORRECT_COUNT
import com.quizit.core.fixture.USER_ID
import com.quizit.core.fixture.createQuiz
import com.quizit.core.fixture.createUserSolvedQuiz
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired

class CustomQuizRepositoryTest : RepositoryTest() {
    @Autowired
    private lateinit var quizRepository: QuizRepository

    @Autowired
    private lateinit var userSolvedQuizRepository: UserSolvedQuizRepository

    init {
        "findQuizDetails()는 퀴즈 통계와 사용자 풀이 정보를 조회한다." {
            val quiz = quizRepository.save(createQuiz(id = null))
            userSolvedQuizRepository.save(
                createUserSolvedQuiz(
                    id = null,
                    userId = USER_ID,
                    quizId = quiz.id!!,
                    selectedOptionId = QUIZ_OPTION_ID
                )
            )
            userSolvedQuizRepository.save(
                createUserSolvedQuiz(
                    id = null,
                    userId = OTHER_USER_ID,
                    quizId = quiz.id!!,
                    isCorrect = INCORRECT_QUIZ_FILTER
                )
            )

            val results = quizRepository.findQuizDetails(userId = USER_ID, chapterId = quiz.chapterId)

            results shouldHaveSize QUIZ_DETAIL_RESULT_SIZE
            results.first().id shouldBe quiz.id
            results.first().correctCount shouldBe QUIZ_CORRECT_COUNT
            results.first().incorrectCount shouldBe QUIZ_OTHER_INCORRECT_COUNT
            results.first().selectedOptionId shouldBe QUIZ_OPTION_ID
            results.first().isCorrect shouldBe true
        }
    }
}
