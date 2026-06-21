package com.quizit.core.domain.course.service

import com.quizit.core.domain.course.repository.CourseRepository
import com.quizit.core.fixture.COURSE_CURRICULUM_ID
import com.quizit.core.fixture.COURSE_ID
import com.quizit.core.fixture.COURSE_SERVICE_SOLVED_COUNT
import com.quizit.core.fixture.COURSE_SERVICE_TOTAL_COUNT
import com.quizit.core.fixture.USER_ID
import com.quizit.core.fixture.createCourseProjections
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

class CourseServiceTest : BehaviorSpec() {
    override fun isolationMode(): IsolationMode = IsolationMode.InstancePerLeaf

    private val courseRepository = mockk<CourseRepository>()
    private val courseService = CourseService(courseRepository)

    init {
        given("getCoursesByCurriculumId()는") {
            every {
                courseRepository.findAllByCurriculumId(curriculumId = COURSE_CURRICULUM_ID, userId = USER_ID)
            } returns createCourseProjections()

            `when`("코스 projection을 조회하면") {
                then("코스 결과로 변환한다.") {
                    val result =
                        courseService.getCoursesByCurriculumId(
                            curriculumId = COURSE_CURRICULUM_ID,
                            userId = USER_ID
                        ).first()

                    result.id shouldBe COURSE_ID
                    result.totalCount shouldBe COURSE_SERVICE_TOTAL_COUNT
                    result.solvedCount shouldBe COURSE_SERVICE_SOLVED_COUNT
                }
            }
        }
    }
}
