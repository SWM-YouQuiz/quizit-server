package com.quizit.core.fixture

import com.quizit.core.domain.auth.dto.command.LoginCommand
import com.quizit.core.domain.auth.dto.command.RefreshCommand
import com.quizit.core.domain.auth.dto.response.GetOAuthUserResponse
import com.quizit.core.domain.auth.dto.result.LoginResult
import com.quizit.core.domain.auth.dto.result.RefreshResult
import com.quizit.core.domain.auth.entity.RefreshToken
import com.quizit.core.domain.user.entity.SocialProvider
import com.quizit.core.global.jwt.JwtProperties
import java.time.Duration
import java.util.UUID
import javax.crypto.spec.SecretKeySpec

const val ACCESS_TOKEN: String = "access-token"
const val OAUTH_TOKEN: String = "oauth-token"
const val REFRESH_TOKEN_CONTENT: String = "refresh-token"
const val OTHER_REFRESH_TOKEN_CONTENT: String = "other-token"
const val DELETED_REFRESH_TOKEN_COUNT: Int = 1
val REFRESH_TOKEN_EXPIRATION: Duration = Duration.ofDays(7)
val ACCESS_TOKEN_EXPIRATION: Duration = Duration.ofHours(1)

fun createGetOAuthUserResponse(
    id: String = USER_SOCIAL_ID,
    socialProvider: SocialProvider = SocialProvider.GOOGLE,
    email: String = USER_EMAIL,
    firstName: String = USER_FIRST_NAME,
    lastName: String = USER_LAST_NAME,
    imageUrl: String = USER_IMAGE_URL
): GetOAuthUserResponse =
    GetOAuthUserResponse(
        id = id,
        socialProvider = socialProvider,
        email = email,
        firstName = firstName,
        lastName = lastName,
        imageUrl = imageUrl
    )

fun createLoginCommand(
    oAuthToken: String = OAUTH_TOKEN,
    provider: SocialProvider = SocialProvider.GOOGLE
): LoginCommand =
    LoginCommand(
        oAuthToken = oAuthToken,
        provider = provider
    )

fun createRefreshCommand(refreshToken: String = REFRESH_TOKEN_CONTENT): RefreshCommand =
    RefreshCommand(refreshToken = refreshToken)

fun createJwtProperties(
    accessTokenExpiration: Duration = ACCESS_TOKEN_EXPIRATION,
    refreshTokenExpiration: Duration = REFRESH_TOKEN_EXPIRATION
): JwtProperties =
    JwtProperties(
        accessTokenExpiration = accessTokenExpiration,
        refreshTokenExpiration = refreshTokenExpiration,
        secretKey = SecretKeySpec(ByteArray(32) { 1 }, "HmacSHA256")
    )

fun createLoginResult(
    accessToken: String = ACCESS_TOKEN,
    refreshToken: String = REFRESH_TOKEN_CONTENT
): LoginResult =
    LoginResult(
        accessToken = accessToken,
        refreshToken = refreshToken
    )

fun createRefreshResult(
    accessToken: String = ACCESS_TOKEN,
    refreshToken: String = REFRESH_TOKEN_CONTENT
): RefreshResult =
    RefreshResult(
        accessToken = accessToken,
        refreshToken = refreshToken
    )

fun createRefreshToken(
    userId: UUID = USER_ID,
    content: String = REFRESH_TOKEN_CONTENT,
    expiration: Long = REFRESH_TOKEN_EXPIRATION.toSeconds()
): RefreshToken =
    RefreshToken(
        userId = userId,
        content = content,
        expiration = expiration
    )
