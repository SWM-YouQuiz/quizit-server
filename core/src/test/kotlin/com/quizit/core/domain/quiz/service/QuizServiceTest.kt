package com.quizit.core.domain.quiz.service

import com.quizit.core.domain.quiz.exception.QuizNotFoundException
import com.quizit.core.domain.quiz.exception.QuizOptionNotFoundException
import com.quizit.core.domain.quiz.repository.QuizBookmarkRepository
import com.quizit.core.domain.quiz.repository.QuizOptionRepository
import com.quizit.core.domain.quiz.repository.QuizReactionRepository
import com.quizit.core.domain.quiz.repository.QuizRepository
import com.quizit.core.domain.user.entity.UserSolvedQuiz
import com.quizit.core.domain.user.repository.UserSolvedQuizRepository
import com.quizit.core.fixture.QUIZ_CHAPTER_ID
import com.quizit.core.fixture.QUIZ_ID
import com.quizit.core.fixture.QUIZ_OPTION_ID
import com.quizit.core.fixture.QUIZ_RESULT_SIZE
import com.quizit.core.fixture.QUIZ_SOLUTION
import com.quizit.core.fixture.SOLVED_QUIZ_FILTER
import com.quizit.core.fixture.UPDATED_REACTION_TYPE
import com.quizit.core.fixture.USER_ID
import com.quizit.core.fixture.createGetSolvedQuizzesQuery
import com.quizit.core.fixture.createGradeQuizCommand
import com.quizit.core.fixture.createMarkQuizCommand
import com.quizit.core.fixture.createQuiz
import com.quizit.core.fixture.createQuizDetailProjections
import com.quizit.core.fixture.createQuizOptions
import com.quizit.core.fixture.createQuizReaction
import com.quizit.core.fixture.createReactQuizCommand
import com.quizit.core.fixture.createSolvedQuizDetailProjections
import com.quizit.core.fixture.createUnsolvedQuizDetailProjections
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import java.util.Optional

class QuizServiceTest : BehaviorSpec() {
    override fun isolationMode(): IsolationMode = IsolationMode.InstancePerLeaf

    private val quizRepository = mockk<QuizRepository>()
    private val quizBookmarkRepository = mockk<QuizBookmarkRepository>()
    private val quizReactionRepository = mockk<QuizReactionRepository>()
    private val quizOptionRepository = mockk<QuizOptionRepository>()
    private val userSolvedQuizRepository = mockk<UserSolvedQuizRepository>()
    private val quizService =
        QuizService(
            quizRepository = quizRepository,
            quizBookmarkRepository = quizBookmarkRepository,
            quizReactionRepository = quizReactionRepository,
            quizOptionRepository = quizOptionRepository,
            userSolvedQuizRepository = userSolvedQuizRepository
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

        given("markQuiz()는") {
            `when`("북마크가 없으면") {
                every { quizRepository.existsById(QUIZ_ID) } returns true
                every { quizBookmarkRepository.existsByQuizIdAndUserId(QUIZ_ID, USER_ID) } returns false
                every { quizBookmarkRepository.save(any()) } answers { firstArg() }

                then("북마크를 저장한다.") {
                    quizService.markQuiz(USER_ID, createMarkQuizCommand())

                    verify(exactly = 1) { quizBookmarkRepository.save(any()) }
                }
            }

            `when`("북마크가 있으면") {
                every { quizRepository.existsById(QUIZ_ID) } returns true
                every { quizBookmarkRepository.existsByQuizIdAndUserId(QUIZ_ID, USER_ID) } returns true
                every { quizBookmarkRepository.deleteByQuizIdAndUserId(QUIZ_ID, USER_ID) } just runs

                then("북마크를 삭제한다.") {
                    quizService.markQuiz(USER_ID, createMarkQuizCommand())

                    verify(exactly = 1) { quizBookmarkRepository.deleteByQuizIdAndUserId(QUIZ_ID, USER_ID) }
                }
            }

            `when`("퀴즈가 없으면") {
                every { quizRepository.existsById(QUIZ_ID) } returns false

                then("예외를 던진다.") {
                    shouldThrow<QuizNotFoundException> {
                        quizService.markQuiz(USER_ID, createMarkQuizCommand())
                    }
                }
            }
        }

        given("reactQuiz()는") {
            `when`("기존 반응이 있으면") {
                val reaction = createQuizReaction()
                every { quizRepository.existsById(QUIZ_ID) } returns true
                every { quizReactionRepository.findByQuizIdAndUserId(QUIZ_ID, USER_ID) } returns reaction
                every { quizReactionRepository.save(any()) } answers { firstArg() }

                then("반응을 수정한다.") {
                    quizService.reactQuiz(USER_ID, createReactQuizCommand())

                    reaction.reactionType shouldBe UPDATED_REACTION_TYPE
                    verify(exactly = 1) { quizReactionRepository.save(reaction) }
                }
            }
        }

        given("gradeQuiz()는") {
            `when`("처음 푸는 퀴즈이면") {
                every { quizRepository.findById(QUIZ_ID) } returns Optional.of(createQuiz())
                every { quizOptionRepository.findByIdAndQuizId(QUIZ_OPTION_ID, QUIZ_ID) } returns
                    createQuizOptions().first()
                every { userSolvedQuizRepository.existsByUserIdAndQuizId(USER_ID, QUIZ_ID) } returns false
                every { userSolvedQuizRepository.save(any()) } answers { firstArg() }

                then("풀이 이력을 저장하고 채점 결과를 반환한다.") {
                    val result =
                        quizService.gradeQuiz(
                            userId = USER_ID,
                            command = createGradeQuizCommand()
                        )

                    result.isCorrect shouldBe true
                    result.solution shouldBe QUIZ_SOLUTION
                    verify(exactly = 1) {
                        userSolvedQuizRepository.save(
                            match<UserSolvedQuiz> {
                                it.userId == USER_ID &&
                                    it.quizId == QUIZ_ID &&
                                    it.selectedOptionId == QUIZ_OPTION_ID &&
                                    it.isCorrect
                            }
                        )
                    }
                }
            }

            `when`("이미 푼 퀴즈이면") {
                every { quizRepository.findById(QUIZ_ID) } returns Optional.of(createQuiz())
                every { quizOptionRepository.findByIdAndQuizId(QUIZ_OPTION_ID, QUIZ_ID) } returns
                    createQuizOptions().first()
                every { userSolvedQuizRepository.existsByUserIdAndQuizId(USER_ID, QUIZ_ID) } returns true

                then("풀이 이력을 새로 저장하지 않는다.") {
                    val result =
                        quizService.gradeQuiz(
                            userId = USER_ID,
                            command = createGradeQuizCommand()
                        )

                    result.solution shouldBe QUIZ_SOLUTION
                    verify(exactly = 0) { userSolvedQuizRepository.save(any()) }
                }
            }

            `when`("선택지가 퀴즈에 속하지 않으면") {
                every { quizRepository.findById(QUIZ_ID) } returns Optional.of(createQuiz())
                every { quizOptionRepository.findByIdAndQuizId(QUIZ_OPTION_ID, QUIZ_ID) } returns null

                then("예외를 던진다.") {
                    shouldThrow<QuizOptionNotFoundException> {
                        quizService.gradeQuiz(
                            userId = USER_ID,
                            command = createGradeQuizCommand()
                        )
                    }
                }
            }
        }
    }
}
