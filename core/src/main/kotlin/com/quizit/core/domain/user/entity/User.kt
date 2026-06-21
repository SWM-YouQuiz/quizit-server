package com.quizit.core.domain.user.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.util.*

@Table("user")
class User(
    @Id
    var id: UUID? = null,
    val socialId: String,
    val socialProvider: SocialProvider,
    var email: String,
    var firstName: String,
    var lastName: String,
    var imageUrl: String,
    val role: Role = Role.MEMBER,
    val status: UserStatus = UserStatus.ACTIVE
)
