package com.quizit.api.domain.curriculum.controller

import com.quizit.api.domain.curriculum.dto.response.GetCurriculumResponse
import com.quizit.api.global.annotation.AuthenticationId
import com.quizit.core.domain.curriculum.service.CurriculumService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*
import kotlin.collections.map

@RestController
@RequestMapping("/api/v1")
class CurriculumController(
    private val curriculumService: CurriculumService
) {
    @GetMapping("/curriculums")
    fun getCurriculums(
        @AuthenticationId
        userId: UUID
    ): List<GetCurriculumResponse> =
        curriculumService.getCurriculums(userId)
            .map { GetCurriculumResponse.from(it) }
}
