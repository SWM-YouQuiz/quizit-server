package com.quizit.core.domain.auth.client

import com.quizit.core.domain.auth.dto.request.GetOAuthUserRequest
import com.quizit.core.domain.auth.dto.response.GetOAuthUserResponse
import com.quizit.core.domain.user.entity.SocialProvider

sealed class OAuthClient(
    val socialProvider: SocialProvider
) {
    protected companion object {
        const val AUTHORIZATION_HEADER_PREFIX = "Bearer "
    }

    abstract fun getOAuthUser(request: GetOAuthUserRequest): GetOAuthUserResponse
}
