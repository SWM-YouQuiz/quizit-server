package com.quizit.api.domain.curriculum.controller

import com.ninjasquad.springmockk.MockkBean
import com.quizit.api.common.ControllerTest
import com.quizit.api.domain.curriculum.controller.CurriculumController
import com.quizit.api.extension.expectBody
import com.quizit.api.extension.expectStatus
import com.quizit.api.fixture.createGetCurriculumResponses
import com.quizit.core.domain.curriculum.service.CurriculumService
import com.quizit.core.fixture.USER_ID
import com.quizit.core.fixture.createGetCurriculumResults
import io.mockk.every
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest

@WebMvcTest(CurriculumController::class)
class CurriculumControllerTest : ControllerTest() {
    @MockkBean
    private lateinit var curriculumService: CurriculumService

    init {
        describe("getCurriculums()는") {
            it("커리큘럼 목록을 반환한다.") {
                every { curriculumService.getCurriculums(USER_ID) } returns createGetCurriculumResults()
                authenticate(USER_ID)

                webClient
                    .get()
                    .uri("/curriculums")
                    .exchange()
                    .expectStatus(200)
                    .expectBody(createGetCurriculumResponses())
            }
        }
    }
}
