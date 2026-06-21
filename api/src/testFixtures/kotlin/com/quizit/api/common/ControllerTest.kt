package com.quizit.api.common

import com.quizit.api.global.security.QuizitAuthentication
import com.quizit.core.domain.user.entity.Role
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.extensions.spring.SpringExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.test.web.servlet.client.MockMvcWebTestClient
import org.springframework.web.context.WebApplicationContext
import java.util.*

abstract class ControllerTest(
    private val version: Int = 1
) : DescribeSpec() {
    @Autowired
    private lateinit var webApplicationContext: WebApplicationContext

    protected val webClient by lazy {
        MockMvcWebTestClient
            .bindToApplicationContext(webApplicationContext)
            .configureClient()
            .baseUrl("/api/v$version")
            .build()
    }

    init {
        afterEach {
            SecurityContextHolder.clearContext()
        }
    }

    protected fun authenticate(
        userId: UUID,
        role: Role = Role.MEMBER
    ) {
        SecurityContextHolder.getContext().authentication =
            QuizitAuthentication(
                id = userId,
                role = role
            )
    }

    override fun extensions() = listOf(SpringExtension)
}
