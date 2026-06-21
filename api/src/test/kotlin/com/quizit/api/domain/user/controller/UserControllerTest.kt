package com.quizit.api.domain.user.controller

import com.ninjasquad.springmockk.MockkBean
import com.quizit.api.common.ControllerTest
import com.quizit.api.domain.user.controller.UserController
import com.quizit.api.extension.expectBody
import com.quizit.api.extension.expectStatus
import com.quizit.api.fixture.createGetSolvedQuizResponses
import com.quizit.api.fixture.createGetUserRankResponses
import com.quizit.api.fixture.createGetUserResponse
import com.quizit.core.domain.quiz.service.QuizService
import com.quizit.core.domain.user.service.UserService
import com.quizit.core.fixture.SOLVED_QUIZ_FILTER
import com.quizit.core.fixture.USER_ID
import com.quizit.core.fixture.createEmptySolvedQuizResults
import com.quizit.core.fixture.createGetSolvedQuizzesQuery
import com.quizit.core.fixture.createGetUserRankResults
import com.quizit.core.fixture.createGetUserResult
import io.mockk.every
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest

@WebMvcTest(UserController::class)
class UserControllerTest : ControllerTest() {
    @MockkBean
    private lateinit var userService: UserService

    @MockkBean
    private lateinit var quizService: QuizService

    init {
        describe("getMe()는") {
            it("내 정보를 반환한다.") {
                every { userService.getUserById(USER_ID) } returns createGetUserResult()
                authenticate(USER_ID)

                webClient
                    .get()
                    .uri("/users/me")
                    .exchange()
                    .expectStatus(200)
                    .expectBody(createGetUserResponse())
            }
        }

        describe("getRanking()은") {
            it("사용자 랭킹을 반환한다.") {
                every { userService.getUserRanks() } returns createGetUserRankResults()

                webClient
                    .get()
                    .uri("/users/me/rankings")
                    .exchange()
                    .expectStatus(200)
                    .expectBody(createGetUserRankResponses())
            }
        }

        describe("getSolvedQuizzes()는") {
            it("풀이 퀴즈 목록 조회를 위임한다.") {
                every {
                    quizService.getSolvedQuizzes(userId = USER_ID, query = createGetSolvedQuizzesQuery())
                } returns createEmptySolvedQuizResults()
                authenticate(USER_ID)

                webClient
                    .get()
                    .uri { it.path("/users/me/solved-quizzes").queryParam("isCorrect", SOLVED_QUIZ_FILTER).build() }
                    .exchange()
                    .expectStatus(200)
                    .expectBody(createGetSolvedQuizResponses())
            }
        }
    }
}
