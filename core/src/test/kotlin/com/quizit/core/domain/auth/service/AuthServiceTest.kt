package com.quizit.core.domain.auth.service

import com.quizit.core.domain.auth.client.GoogleOAuthClient
import com.quizit.core.domain.auth.dto.request.GetOAuthUserRequest
import com.quizit.core.domain.auth.exception.InvalidAuthenticationException
import com.quizit.core.domain.auth.exception.RefreshTokenNotFoundException
import com.quizit.core.domain.auth.repository.RefreshTokenRepository
import com.quizit.core.domain.user.entity.SocialProvider
import com.quizit.core.domain.user.entity.User
import com.quizit.core.domain.user.repository.UserRepository
import com.quizit.core.fixture.ACCESS_TOKEN
import com.quizit.core.fixture.DELETED_REFRESH_TOKEN_COUNT
import com.quizit.core.fixture.OAUTH_TOKEN
import com.quizit.core.fixture.OTHER_REFRESH_TOKEN_CONTENT
import com.quizit.core.fixture.REFRESH_TOKEN_CONTENT
import com.quizit.core.fixture.USER_ID
import com.quizit.core.fixture.USER_SOCIAL_ID
import com.quizit.core.fixture.createGetOAuthUserResponse
import com.quizit.core.fixture.createJwtProperties
import com.quizit.core.fixture.createLoginCommand
import com.quizit.core.fixture.createRefreshCommand
import com.quizit.core.fixture.createRefreshToken
import com.quizit.core.fixture.createUser
import com.quizit.core.global.jwt.JwtProvider
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

class AuthServiceTest : BehaviorSpec() {
    override fun isolationMode(): IsolationMode = IsolationMode.InstancePerLeaf

    private val userRepository = mockk<UserRepository>()
    private val refreshTokenRepository = mockk<RefreshTokenRepository>()
    private val oAuthClient = mockk<GoogleOAuthClient>()
    private val jwtProvider = mockk<JwtProvider>()
    private val jwtProperties = createJwtProperties()
    private val authService =
        AuthService(
            userRepository = userRepository,
            refreshTokenRepository = refreshTokenRepository,
            oAuthClients = listOf(oAuthClient),
            jwtProvider = jwtProvider,
            jwtProperties = jwtProperties
        )

    init {
        given("login()은") {
            every { oAuthClient.socialProvider } returns SocialProvider.GOOGLE
            every { oAuthClient.getOAuthUser(GetOAuthUserRequest(accessToken = OAUTH_TOKEN)) } returns
                createGetOAuthUserResponse()
            every { userRepository.findBySocialIdAndSocialProvider(USER_SOCIAL_ID, SocialProvider.GOOGLE) } returns
                createUser()
            every { userRepository.save(any()) } returns createUser()
            every { jwtProvider.createToken(jwtProperties.accessTokenExpiration, any<User>()) } returns ACCESS_TOKEN
            every { jwtProvider.createToken(jwtProperties.refreshTokenExpiration, any<User>()) } returns
                REFRESH_TOKEN_CONTENT
            every { refreshTokenRepository.save(any()) } returns createRefreshToken()

            `when`("OAuth 사용자 정보를 조회하면") {
                then("인증 토큰을 발급하고 refresh token을 저장한다.") {
                    val result =
                        authService.login(
                            createLoginCommand()
                        )

                    result.accessToken shouldBe ACCESS_TOKEN
                    result.refreshToken shouldBe REFRESH_TOKEN_CONTENT
                    verify(exactly = 1) { refreshTokenRepository.save(any()) }
                }
            }
        }

        given("refresh()는") {
            `when`("저장된 refresh token과 요청 token이 같으면") {
                every { jwtProvider.extractUserId(REFRESH_TOKEN_CONTENT) } returns USER_ID
                every { refreshTokenRepository.findByUserId(USER_ID) } returns createRefreshToken()
                every { userRepository.findUserById(USER_ID) } returns createUser()
                every { jwtProvider.createToken(jwtProperties.accessTokenExpiration, any<User>()) } returns ACCESS_TOKEN
                every { jwtProvider.createToken(jwtProperties.refreshTokenExpiration, any<User>()) } returns
                    REFRESH_TOKEN_CONTENT
                every { refreshTokenRepository.save(any()) } returns createRefreshToken()

                then("새 토큰을 발급한다.") {
                    val result = authService.refresh(createRefreshCommand())

                    result.accessToken shouldBe ACCESS_TOKEN
                    result.refreshToken shouldBe REFRESH_TOKEN_CONTENT
                }
            }

            `when`("저장된 refresh token이 없으면") {
                every { jwtProvider.extractUserId(REFRESH_TOKEN_CONTENT) } returns USER_ID
                every { refreshTokenRepository.findByUserId(USER_ID) } returns null

                then("예외를 던진다.") {
                    shouldThrow<RefreshTokenNotFoundException> {
                        authService.refresh(createRefreshCommand())
                    }
                }
            }

            `when`("저장된 refresh token과 요청 token이 다르면") {
                every { jwtProvider.extractUserId(REFRESH_TOKEN_CONTENT) } returns USER_ID
                every { refreshTokenRepository.findByUserId(USER_ID) } returns
                    createRefreshToken(content = OTHER_REFRESH_TOKEN_CONTENT)
                every { refreshTokenRepository.deleteByUserId(USER_ID) } returns DELETED_REFRESH_TOKEN_COUNT

                then("저장된 token을 삭제하고 예외를 던진다.") {
                    shouldThrow<InvalidAuthenticationException> {
                        authService.refresh(createRefreshCommand())
                    }
                    verify(exactly = 1) { refreshTokenRepository.deleteByUserId(USER_ID) }
                }
            }
        }

        given("logout()은") {
            every { refreshTokenRepository.deleteByUserId(USER_ID) } returns DELETED_REFRESH_TOKEN_COUNT

            `when`("사용자 id를 받으면") {
                then("refresh token을 삭제한다.") {
                    authService.logout(USER_ID)

                    verify(exactly = 1) { refreshTokenRepository.deleteByUserId(USER_ID) }
                }
            }
        }
    }
}
