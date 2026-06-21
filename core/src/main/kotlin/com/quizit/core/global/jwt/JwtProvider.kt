package com.quizit.core.global.jwt

import com.quizit.core.domain.auth.exception.InvalidAuthenticationException
import com.quizit.core.domain.user.entity.User
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import org.springframework.stereotype.Component
import java.time.Duration
import java.util.*

@Component
class JwtProvider(
    private val jwtProperties: JwtProperties
) {
    companion object {
        private const val TOKEN_ISSUER = "quizit"
        const val USER_ID_CLAIM = "id"
        const val USER_ROLE_CLAIM = "role"
    }

    fun createToken(
        expiration: Duration,
        user: User
    ): String =
        createToken(
            expiration = expiration,
            payload =
                mapOf(
                    USER_ID_CLAIM to user.id.toString(),
                    USER_ROLE_CLAIM to user.role.name
                )
        )

    fun extractUserId(token: String): UUID =
        try {
            extractPayload(token)
                .run { get(USER_ID_CLAIM) as String }
                .let { UUID.fromString(it) }
        } catch (_: JwtException) {
            throw InvalidAuthenticationException()
        }

    fun createToken(
        expiration: Duration,
        payload: Map<String, *>
    ): String {
        val now = Date()

        return Jwts
            .builder()
            .issuedAt(now)
            .expiration(Date(now.time + expiration.toMillis()))
            .issuer(TOKEN_ISSUER)
            .claims(payload)
            .signWith(jwtProperties.secretKey)
            .compact()
    }

    fun extractPayload(token: String): Map<String, *> =
        Jwts
            .parser()
            .verifyWith(jwtProperties.secretKey)
            .build()
            .parseSignedClaims(token)
            .payload
}
