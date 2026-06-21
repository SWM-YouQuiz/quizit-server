package com.quizit.api.domain.quiz.controller

import com.ninjasquad.springmockk.MockkBean
import com.quizit.api.common.ControllerTest
import com.quizit.api.extension.expectBody
import com.quizit.api.extension.expectStatus
import com.quizit.api.fixture.createGetQuizResponses
import com.quizit.api.fixture.createGradeQuizRequest
import com.quizit.api.fixture.createGradeQuizResponse
import com.quizit.api.fixture.createReactQuizRequest
import com.quizit.core.domain.quiz.service.QuizService
import com.quizit.core.fixture.*
import io.mockk.every
import io.mockk.just
import io.mockk.runs
import io.mockk.verify
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest

@WebMvcTest(QuizController::class)
class QuizControllerTest : ControllerTest() {
    @MockkBean
    private lateinit var quizService: QuizService

    init {
        describe("markQuiz()는") {
            it("북마크 토글을 요청한다.") {
                every {
                    quizService.markQuiz(userId = USER_ID, command = createMarkQuizCommand())
                } just runs
                authenticate(USER_ID)

                webClient
                    .post()
                    .uri("/users/me/quizzes/{quiz_id}/bookmarks", QUIZ_ID)
                    .exchange()
                    .expectStatus(200)

                verify(exactly = 1) {
                    quizService.markQuiz(userId = USER_ID, command = createMarkQuizCommand())
                }
            }
        }

        describe("reactQuiz()는") {
            it("퀴즈 반응을 요청한다.") {
                every {
                    quizService.reactQuiz(userId = USER_ID, command = createReactQuizCommand())
                } just runs
                authenticate(USER_ID)

                webClient
                    .post()
                    .uri("/users/me/quizzes/{quiz_id}/reactions", QUIZ_ID)
                    .bodyValue(createReactQuizRequest())
                    .exchange()
                    .expectStatus(200)

                verify(exactly = 1) {
                    quizService.reactQuiz(userId = USER_ID, command = createReactQuizCommand())
                }
            }
        }

        describe("gradeQuiz()는") {
            it("채점 결과를 반환한다.") {
                every {
                    quizService.gradeQuiz(
                        userId = USER_ID,
                        command = createGradeQuizCommand()
                    )
                } returns createGradeQuizResult()
                authenticate(USER_ID)

                webClient
                    .post()
                    .uri("/users/me/quizzes/{quiz_id}/grade", QUIZ_ID)
                    .bodyValue(createGradeQuizRequest())
                    .exchange()
                    .expectStatus(200)
                    .expectBody(createGradeQuizResponse())
            }
        }

        describe("getQuizzesByChapterId()는") {
            it("챕터 퀴즈 목록을 반환한다.") {
                every {
                    quizService.getQuizzesByChapterId(userId = USER_ID, chapterId = QUIZ_CHAPTER_ID)
                } returns createGetQuizResults()
                authenticate(USER_ID)

                webClient
                    .get()
                    .uri("/chapters/{chapter_id}/quizzes", QUIZ_CHAPTER_ID)
                    .exchange()
                    .expectStatus(200)
                    .expectBody(createGetQuizResponses())
            }
        }
    }
}
