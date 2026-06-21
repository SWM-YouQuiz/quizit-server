package com.quizit.api.domain.user.controller

import com.ninjasquad.springmockk.MockkBean
import com.quizit.api.common.ControllerTest
import com.quizit.api.domain.user.controller.UserController
import com.quizit.api.extension.expectBody
import com.quizit.api.extension.expectStatus
import com.quizit.api.fixture.createGetUserRankResponses
import com.quizit.api.fixture.createGetUserResponse
import com.quizit.core.domain.user.service.UserService
import com.quizit.core.fixture.USER_ID
import com.quizit.core.fixture.createGetUserRankResults
import com.quizit.core.fixture.createGetUserResult
import io.mockk.every
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest

@WebMvcTest(UserController::class)
class UserControllerTest : ControllerTest() {
    @MockkBean
    private lateinit var userService: UserService

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
    }
}
