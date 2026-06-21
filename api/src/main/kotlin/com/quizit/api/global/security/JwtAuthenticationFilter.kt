package com.quizit.api.global.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.convertValue
import com.quizit.core.global.jwt.JwtProvider
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

class JwtAuthenticationFilter(
    private val jwtProvider: JwtProvider,
    private val objectMapper: ObjectMapper
) : OncePerRequestFilter() {
    private companion object {
        const val AUTHORIZATION_HEADER_PREFIX = "Bearer "
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        request
            .getHeader(HttpHeaders.AUTHORIZATION)
            ?.runCatching { jwtProvider.extractPayload(getBearerToken()) }
            ?.onSuccess {
                SecurityContextHolder.getContext().authentication =
                    objectMapper.convertValue<QuizitAuthentication>(it)
            }

        filterChain.doFilter(request, response)
    }

    private fun String.getBearerToken(): String =
        if (startsWith(AUTHORIZATION_HEADER_PREFIX)) {
            removePrefix(AUTHORIZATION_HEADER_PREFIX)
        } else {
            throw IllegalArgumentException()
        }
}
