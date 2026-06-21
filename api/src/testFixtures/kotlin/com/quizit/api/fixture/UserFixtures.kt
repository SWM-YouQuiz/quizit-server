package com.quizit.api.fixture

import com.quizit.api.domain.user.dto.response.GetUserRankResponse
import com.quizit.api.domain.user.dto.response.GetUserResponse
import com.quizit.core.domain.user.entity.Role
import com.quizit.core.domain.user.entity.SocialProvider
import com.quizit.core.fixture.USER_EMAIL
import com.quizit.core.fixture.USER_FIRST_NAME
import com.quizit.core.fixture.USER_ID
import com.quizit.core.fixture.USER_IMAGE_URL
import com.quizit.core.fixture.USER_LAST_NAME
import java.util.*

fun createGetUserResponse(
    id: UUID = USER_ID,
    email: String = USER_EMAIL,
    socialProvider: SocialProvider = SocialProvider.GOOGLE,
    firstName: String = USER_FIRST_NAME,
    lastName: String = USER_LAST_NAME,
    imageUrl: String = USER_IMAGE_URL,
    answerRate: Double = 0.0,
    role: Role = Role.MEMBER
): GetUserResponse =
    GetUserResponse(
        id = id,
        email = email,
        socialProvider = socialProvider,
        firstName = firstName,
        lastName = lastName,
        imageUrl = imageUrl,
        answerRate = answerRate,
        role = role
    )

fun createGetUserRankResponse(
    firstName: String = USER_FIRST_NAME,
    lastName: String = USER_LAST_NAME,
    imageUrl: String = USER_IMAGE_URL,
    level: Int = 1,
    correctCount: Int = 1
): GetUserRankResponse =
    GetUserRankResponse(
        firstName = firstName,
        lastName = lastName,
        imageUrl = imageUrl,
        level = level,
        correctCount = correctCount
    )

fun createGetUserRankResponses(): List<GetUserRankResponse> = listOf(createGetUserRankResponse())
