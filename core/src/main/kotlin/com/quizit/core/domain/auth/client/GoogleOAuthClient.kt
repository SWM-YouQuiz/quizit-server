package com.quizit.core.domain.auth.client

import com.quizit.core.domain.auth.dto.request.GetOAuthUserRequest
import com.quizit.core.domain.auth.dto.response.GetOAuthUserResponse
import com.quizit.core.domain.auth.exception.InvalidOAuthTokenException
import com.quizit.core.domain.user.entity.SocialProvider
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient
import org.springframework.web.client.requiredBody

@Component
class GoogleOAuthClient(
    private val restClient: RestClient
) : OAuthClient(socialProvider = SocialProvider.GOOGLE) {
    private companion object {
        const val USERINFO_ENDPOINT = "https://openidconnect.googleapis.com/v1/userinfo"
        const val ID_FIELD = "sub"
        const val EMAIL_FIELD = "email"
        const val FIRST_NAME_FIELD = "given_name"
        const val LAST_NAME_FIELD = "family_name"
        const val IMAGE_URL_FIELD = "picture"
    }

    override fun getOAuthUser(request: GetOAuthUserRequest): GetOAuthUserResponse =
        restClient
            .get()
            .uri(USERINFO_ENDPOINT)
            .header(HttpHeaders.AUTHORIZATION, AUTHORIZATION_HEADER_PREFIX + request.accessToken)
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError) { _, _ -> throw InvalidOAuthTokenException() }
            .requiredBody<Map<String, String>>()
            .run {
                GetOAuthUserResponse(
                    id = getValue(ID_FIELD),
                    socialProvider = socialProvider,
                    email = getValue(EMAIL_FIELD),
                    firstName = getValue(FIRST_NAME_FIELD),
                    lastName = getValue(LAST_NAME_FIELD),
                    imageUrl = getValue(IMAGE_URL_FIELD)
                )
            }
}
