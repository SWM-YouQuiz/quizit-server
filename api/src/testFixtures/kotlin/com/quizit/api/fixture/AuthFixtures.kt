package com.quizit.api.fixture

import com.quizit.api.domain.auth.dto.request.LoginRequest
import com.quizit.api.domain.auth.dto.request.RefreshRequest
import com.quizit.api.domain.auth.dto.response.LoginResponse
import com.quizit.api.domain.auth.dto.response.RefreshResponse
import com.quizit.core.domain.user.entity.SocialProvider
import com.quizit.core.fixture.ACCESS_TOKEN
import com.quizit.core.fixture.OAUTH_TOKEN
import com.quizit.core.fixture.REFRESH_TOKEN_CONTENT

fun createLoginRequest(
    oAuthToken: String = OAUTH_TOKEN,
    provider: SocialProvider = SocialProvider.GOOGLE
): LoginRequest =
    LoginRequest(
        oAuthToken = oAuthToken,
        provider = provider
    )

fun createRefreshRequest(refreshToken: String = REFRESH_TOKEN_CONTENT): RefreshRequest =
    RefreshRequest(refreshToken = refreshToken)

fun createLoginResponse(
    accessToken: String = ACCESS_TOKEN,
    refreshToken: String = REFRESH_TOKEN_CONTENT
): LoginResponse =
    LoginResponse(
        accessToken = accessToken,
        refreshToken = refreshToken
    )

fun createRefreshResponse(
    accessToken: String = ACCESS_TOKEN,
    refreshToken: String = REFRESH_TOKEN_CONTENT
): RefreshResponse =
    RefreshResponse(
        accessToken = accessToken,
        refreshToken = refreshToken
    )
