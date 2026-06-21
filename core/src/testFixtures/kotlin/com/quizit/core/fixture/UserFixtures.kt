package com.quizit.core.fixture

import com.quizit.core.domain.user.dto.projection.UserRankProjection
import com.quizit.core.domain.user.dto.result.GetUserRankResult
import com.quizit.core.domain.user.dto.result.GetUserResult
import com.quizit.core.domain.user.entity.Role
import com.quizit.core.domain.user.entity.SocialProvider
import com.quizit.core.domain.user.entity.User
import com.quizit.core.domain.user.entity.UserSolvedQuiz
import com.quizit.core.domain.user.entity.UserStatus
import java.util.*

val USER_ID: UUID = UUID.fromString("0198fb75-7cc9-7d43-b7cc-f05d161d5c44")
val OTHER_USER_ID: UUID = UUID.fromString("0198fb75-7cc9-7d43-b7cc-f05d161d5c48")
val USER_SOLVED_QUIZ_ID: UUID = UUID.fromString("0198fb75-7cc9-7d43-b7cc-f05d161d5c46")
val USER_UNSOLVED_QUIZ_ID: UUID = UUID.fromString("0198fb75-7cc9-7d43-b7cc-f05d161d5c49")
val USER_QUIZ_ID: UUID = UUID.fromString("0198fb75-7cc9-7d43-b7cc-f05d161d5c42")
const val USER_SOCIAL_ID: String = "google-user-id"
const val USER_EMAIL: String = "user@example.com"
const val USER_FIRST_NAME: String = "Quiz"
const val USER_LAST_NAME: String = "User"
const val USER_IMAGE_URL: String = "https://example.com/user.png"
const val USER_RANK_CORRECT_COUNT: Int = 7
const val USER_RANK_RESULT_SIZE: Int = 2
const val USER_CORRECT_QUIZ_COUNT: Int = 1
const val USER_EMPTY_CORRECT_QUIZ_COUNT: Int = 0
const val USER_INCORRECT_QUIZ_FILTER: Boolean = false
const val RANKED_USER_SOCIAL_ID: String = "google-user-id-1"
const val UNRANKED_USER_SOCIAL_ID: String = "google-user-id-2"

fun createUser(
    id: UUID? = USER_ID,
    socialId: String = USER_SOCIAL_ID,
    email: String = USER_EMAIL,
    socialProvider: SocialProvider = SocialProvider.GOOGLE,
    firstName: String = USER_FIRST_NAME,
    lastName: String = USER_LAST_NAME,
    imageUrl: String = USER_IMAGE_URL,
    role: Role = Role.MEMBER,
    status: UserStatus = UserStatus.ACTIVE
): User =
    User(
        id = id,
        socialId = socialId,
        email = email,
        socialProvider = socialProvider,
        firstName = firstName,
        lastName = lastName,
        imageUrl = imageUrl,
        role = role,
        status = status
    )

fun createUserSolvedQuiz(
    id: UUID? = USER_SOLVED_QUIZ_ID,
    userId: UUID = USER_ID,
    quizId: UUID = USER_QUIZ_ID,
    selectedOptionId: UUID? = null,
    isCorrect: Boolean = true
): UserSolvedQuiz =
    UserSolvedQuiz(
        id = id,
        userId = userId,
        quizId = quizId,
        selectedOptionId = selectedOptionId,
        isCorrect = isCorrect
    )

fun createGetUserResult(
    id: UUID = USER_ID,
    email: String = USER_EMAIL,
    socialProvider: SocialProvider = SocialProvider.GOOGLE,
    firstName: String = USER_FIRST_NAME,
    lastName: String = USER_LAST_NAME,
    imageUrl: String = USER_IMAGE_URL,
    answerRate: Double = 0.0,
    role: Role = Role.MEMBER
): GetUserResult =
    GetUserResult(
        id = id,
        email = email,
        socialProvider = socialProvider,
        firstName = firstName,
        lastName = lastName,
        imageUrl = imageUrl,
        answerRate = answerRate,
        role = role
    )

fun createUserRankingProjection(
    firstName: String = USER_FIRST_NAME,
    lastName: String = USER_LAST_NAME,
    imageUrl: String = USER_IMAGE_URL,
    correctCount: Int = 1
): UserRankProjection =
    UserRankProjection(
        firstName = firstName,
        lastName = lastName,
        imageUrl = imageUrl,
        correctCount = correctCount
    )

fun createGetUserRankResult(
    firstName: String = USER_FIRST_NAME,
    lastName: String = USER_LAST_NAME,
    imageUrl: String = USER_IMAGE_URL,
    level: Int = 1,
    correctCount: Int = 1
): GetUserRankResult =
    GetUserRankResult(
        firstName = firstName,
        lastName = lastName,
        imageUrl = imageUrl,
        level = level,
        correctCount = correctCount
    )

fun createGetUserRankResults(): List<GetUserRankResult> = listOf(createGetUserRankResult())

fun createUserRankingProjections(): List<UserRankProjection> =
    listOf(createUserRankingProjection(correctCount = USER_RANK_CORRECT_COUNT))
