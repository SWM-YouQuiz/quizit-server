package com.quizit.api.domain.course.controller

import com.ninjasquad.springmockk.MockkBean
import com.quizit.api.common.ControllerTest
import com.quizit.api.domain.course.controller.CourseController
import com.quizit.api.extension.expectBody
import com.quizit.api.extension.expectStatus
import com.quizit.api.fixture.createGetCourseResponses
import com.quizit.core.domain.course.service.CourseService
import com.quizit.core.fixture.COURSE_CURRICULUM_ID
import com.quizit.core.fixture.USER_ID
import com.quizit.core.fixture.createGetCourseResults
import io.mockk.every
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest

@WebMvcTest(CourseController::class)
class CourseControllerTest : ControllerTest() {
    @MockkBean
    private lateinit var courseService: CourseService

    init {
        describe("getCoursesByCurriculumId()는") {
            it("커리큘럼 코스 목록을 반환한다.") {
                every {
                    courseService.getCoursesByCurriculumId(curriculumId = COURSE_CURRICULUM_ID, userId = USER_ID)
                } returns createGetCourseResults()
                authenticate(USER_ID)

                webClient
                    .get()
                    .uri("/curriculums/{curriculum_id}/courses", COURSE_CURRICULUM_ID)
                    .exchange()
                    .expectStatus(200)
                    .expectBody(createGetCourseResponses())
            }
        }
    }
}
