package com.quizit.api.domain.auth.controller

import com.ninjasquad.springmockk.MockkBean
import com.quizit.api.common.ControllerTest
import com.quizit.api.domain.auth.controller.AuthController
import com.quizit.api.extension.expectBody
import com.quizit.api.extension.expectStatus
import com.quizit.api.fixture.createLoginRequest
import com.quizit.api.fixture.createLoginResponse
import com.quizit.api.fixture.createRefreshRequest
import com.quizit.api.fixture.createRefreshResponse
import com.quizit.core.domain.auth.dto.command.RefreshCommand
import com.quizit.core.domain.auth.service.AuthService
import com.quizit.core.fixture.OAUTH_TOKEN
import com.quizit.core.fixture.REFRESH_TOKEN_CONTENT
import com.quizit.core.fixture.USER_ID
import com.quizit.core.fixture.createLoginResult
import com.quizit.core.fixture.createRefreshResult
import io.mockk.every
import io.mockk.just
import io.mockk.runs
import io.mockk.verify
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest

@WebMvcTest(AuthController::class)
class AuthControllerTest : ControllerTest() {
    @MockkBean
    private lateinit var authService: AuthService

    init {
        describe("login()은") {
            it("로그인 결과를 반환한다.") {
                every { authService.login(createLoginRequest(oAuthToken = OAUTH_TOKEN).toCommand()) } returns
                    createLoginResult()

                webClient
                    .post()
                    .uri("/auth/login")
                    .bodyValue(createLoginRequest(oAuthToken = OAUTH_TOKEN))
                    .exchange()
                    .expectStatus(200)
                    .expectBody(createLoginResponse())
            }
        }

        describe("refresh()는") {
            it("토큰 재발급 결과를 반환한다.") {
                every {
                    authService.refresh(RefreshCommand(refreshToken = REFRESH_TOKEN_CONTENT))
                } returns createRefreshResult()

                webClient
                    .post()
                    .uri("/auth/refresh")
                    .bodyValue(createRefreshRequest())
                    .exchange()
                    .expectStatus(200)
                    .expectBody(createRefreshResponse())
            }
        }

        describe("logout()은") {
            it("로그아웃을 요청한다.") {
                every { authService.logout(USER_ID) } just runs
                authenticate(USER_ID)

                webClient
                    .post()
                    .uri("/auth/logout")
                    .exchange()
                    .expectStatus(200)

                verify(exactly = 1) { authService.logout(USER_ID) }
            }
        }
    }
}
