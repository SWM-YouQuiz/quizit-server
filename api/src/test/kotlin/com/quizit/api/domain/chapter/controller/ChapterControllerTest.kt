package com.quizit.api.domain.chapter.controller

import com.ninjasquad.springmockk.MockkBean
import com.quizit.api.common.ControllerTest
import com.quizit.api.domain.chapter.controller.ChapterController
import com.quizit.api.extension.expectBody
import com.quizit.api.extension.expectStatus
import com.quizit.api.fixture.createGetChapterResponses
import com.quizit.core.domain.chapter.service.ChapterService
import com.quizit.core.fixture.CHAPTER_COURSE_ID
import com.quizit.core.fixture.USER_ID
import com.quizit.core.fixture.createGetChapterResults
import io.mockk.every
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest

@WebMvcTest(ChapterController::class)
class ChapterControllerTest : ControllerTest() {
    @MockkBean
    private lateinit var chapterService: ChapterService

    init {
        describe("getChaptersByCourseId()는") {
            it("코스 챕터 목록을 반환한다.") {
                every {
                    chapterService.getChaptersByCourseId(courseId = CHAPTER_COURSE_ID, userId = USER_ID)
                } returns createGetChapterResults()
                authenticate(USER_ID)

                webClient
                    .get()
                    .uri("/courses/{course_id}/chapters", CHAPTER_COURSE_ID)
                    .exchange()
                    .expectStatus(200)
                    .expectBody(createGetChapterResponses())
            }
        }
    }
}
