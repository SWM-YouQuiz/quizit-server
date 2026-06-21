package com.quizit.core.domain.quiz.service

import com.quizit.core.domain.quiz.repository.QuizOptionRepository
import com.quizit.core.domain.quiz.repository.QuizRepository
import com.quizit.core.fixture.QUIZ_CHAPTER_ID
import com.quizit.core.fixture.QUIZ_ID
import com.quizit.core.fixture.QUIZ_OPTION_ID
import com.quizit.core.fixture.QUIZ_RESULT_SIZE
import com.quizit.core.fixture.QUIZ_SOLUTION
import com.quizit.core.fixture.SOLVED_QUIZ_FILTER
import com.quizit.core.fixture.USER_ID
import com.quizit.core.fixture.createGetSolvedQuizzesQuery
import com.quizit.core.fixture.createQuizDetailProjections
import com.quizit.core.fixture.createQuizOptions
import com.quizit.core.fixture.createSolvedQuizDetailProjections
import com.quizit.core.fixture.createUnsolvedQuizDetailProjections
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

class QuizServiceTest : BehaviorSpec() {
    override fun isolationMode(): IsolationMode = IsolationMode.InstancePerLeaf

    private val quizRepository = mockk<QuizRepository>()
    private val quizOptionRepository = mockk<QuizOptionRepository>()
    private val quizService =
        QuizService(
            quizRepository = quizRepository,
            quizOptionRepository = quizOptionRepository
        )

    init {
        given("getQuizzesByChapterId()는") {
            `when`("사용자가 아직 풀지 않은 퀴즈이면") {
                every {
                    quizRepository.findQuizDetails(userId = USER_ID, chapterId = QUIZ_CHAPTER_ID)
                } returns createUnsolvedQuizDetailProjections()
                every { quizOptionRepository.findAllByQuizIdIn(listOf(QUIZ_ID)) } returns createQuizOptions()

                then("해설과 정답 여부를 숨긴다.") {
                    val result =
                        quizService.getQuizzesByChapterId(
                            userId = USER_ID,
                            chapterId = QUIZ_CHAPTER_ID
                        ).first()

                    result.solution shouldBe null
                    result.options.first().isAnswer shouldBe null
                    result.selectedOptionId shouldBe null
                    result.isCorrect shouldBe null
                }
            }

            `when`("사용자가 푼 퀴즈이면") {
                every {
                    quizRepository.findQuizDetails(userId = USER_ID, chapterId = QUIZ_CHAPTER_ID)
                } returns createQuizDetailProjections()
                every { quizOptionRepository.findAllByQuizIdIn(listOf(QUIZ_ID)) } returns createQuizOptions()

                then("해설과 정답 여부를 반환한다.") {
                    val result =
                        quizService.getQuizzesByChapterId(
                            userId = USER_ID,
                            chapterId = QUIZ_CHAPTER_ID
                        ).first()

                    result.solution shouldBe QUIZ_SOLUTION
                    result.options.first().isAnswer shouldBe true
                    result.selectedOptionId shouldBe QUIZ_OPTION_ID
                    result.isCorrect shouldBe true
                }
            }
        }

        given("getSolvedQuizzes()는") {
            every {
                quizRepository.findSolvedQuizDetails(userId = USER_ID, isCorrect = SOLVED_QUIZ_FILTER)
            } returns createSolvedQuizDetailProjections()
            every { quizOptionRepository.findAllByQuizIdIn(listOf(QUIZ_ID)) } returns createQuizOptions()

            `when`("정답 여부 조건이 있으면") {
                then("풀이 퀴즈 목록을 반환한다.") {
                    val results = quizService.getSolvedQuizzes(USER_ID, createGetSolvedQuizzesQuery())

                    results shouldHaveSize QUIZ_RESULT_SIZE
                    results.first().id shouldBe QUIZ_ID
                    results.first().isCorrect shouldBe SOLVED_QUIZ_FILTER
                    results.first().options.first().isAnswer shouldBe SOLVED_QUIZ_FILTER
                }
            }
        }
    }
}
