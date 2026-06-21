package com.quizit.core.domain.auth.service

import com.quizit.core.domain.auth.client.OAuthClient
import com.quizit.core.domain.auth.dto.command.LoginCommand
import com.quizit.core.domain.auth.dto.command.RefreshCommand
import com.quizit.core.domain.auth.dto.request.GetOAuthUserRequest
import com.quizit.core.domain.auth.dto.result.LoginResult
import com.quizit.core.domain.auth.dto.result.RefreshResult
import com.quizit.core.domain.auth.entity.RefreshToken
import com.quizit.core.domain.auth.exception.InvalidAuthenticationException
import com.quizit.core.domain.auth.exception.RefreshTokenNotFoundException
import com.quizit.core.domain.auth.repository.RefreshTokenRepository
import com.quizit.core.domain.user.entity.User
import com.quizit.core.domain.user.exception.UserNotFoundException
import com.quizit.core.domain.user.repository.UserRepository
import com.quizit.core.global.jwt.JwtProperties
import com.quizit.core.global.jwt.JwtProvider
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val oAuthClients: List<OAuthClient>,
    private val jwtProvider: JwtProvider,
    private val jwtProperties: JwtProperties
) {
    @Transactional
    fun login(command: LoginCommand): LoginResult {
        val getOAuthUserResponse =
            oAuthClients
                .first { it.socialProvider == command.provider }
                .getOAuthUser(
                    GetOAuthUserRequest(
                        accessToken = command.oAuthToken
                    )
                )
        val user =
            userRepository
                .findBySocialIdAndSocialProvider(getOAuthUserResponse.id, getOAuthUserResponse.socialProvider)
                ?.apply {
                    email = getOAuthUserResponse.email
                    firstName = getOAuthUserResponse.firstName
                    lastName = getOAuthUserResponse.lastName
                    imageUrl = getOAuthUserResponse.imageUrl
                }
                ?: User(
                    socialId = getOAuthUserResponse.id,
                    email = getOAuthUserResponse.email,
                    socialProvider = getOAuthUserResponse.socialProvider,
                    firstName = getOAuthUserResponse.firstName,
                    lastName = getOAuthUserResponse.lastName,
                    imageUrl = getOAuthUserResponse.imageUrl
                )

        userRepository.save(user)

        val (accessToken, refreshToken) = user.createAuthenticationTokens()

        return LoginResult(
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }

    @Transactional
    fun refresh(command: RefreshCommand): RefreshResult {
        val userId = jwtProvider.extractUserId(command.refreshToken)
        val refreshToken = refreshTokenRepository.findByUserId(userId) ?: throw RefreshTokenNotFoundException()

        if (refreshToken.content != command.refreshToken) {
            refreshTokenRepository.deleteByUserId(userId)

            throw InvalidAuthenticationException()
        }

        val user = userRepository.findUserById(userId) ?: throw UserNotFoundException()
        val (newAccessToken, newRefreshToken) = user.createAuthenticationTokens()

        return RefreshResult(
            accessToken = newAccessToken,
            refreshToken = newRefreshToken
        )
    }

    fun logout(userId: UUID) {
        refreshTokenRepository.deleteByUserId(userId)
    }

    private fun User.createAuthenticationTokens(): Pair<String, String> {
        val accessToken = jwtProvider.createToken(jwtProperties.accessTokenExpiration, this)
        val refreshToken = jwtProvider.createToken(jwtProperties.refreshTokenExpiration, this)

        refreshTokenRepository
            .save(
                RefreshToken(
                    userId = id!!,
                    content = refreshToken,
                    expiration = jwtProperties.refreshTokenExpiration.seconds
                )
            )

        return accessToken to refreshToken
    }
}
