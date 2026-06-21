package com.quizit.core.domain.curriculum.service

import com.quizit.core.domain.curriculum.repository.CurriculumRepository
import com.quizit.core.fixture.CURRICULUM_ID
import com.quizit.core.fixture.CURRICULUM_SERVICE_SOLVED_COUNT
import com.quizit.core.fixture.CURRICULUM_SERVICE_TOTAL_COUNT
import com.quizit.core.fixture.USER_ID
import com.quizit.core.fixture.createCurriculumDetailProjections
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

class CurriculumServiceTest : BehaviorSpec() {
    override fun isolationMode(): IsolationMode = IsolationMode.InstancePerLeaf

    private val curriculumRepository = mockk<CurriculumRepository>()
    private val curriculumService = CurriculumService(curriculumRepository)

    init {
        given("getCurriculums()는") {
            every { curriculumRepository.findCurriculumDetails(USER_ID) } returns createCurriculumDetailProjections()

            `when`("커리큘럼 projection을 조회하면") {
                then("커리큘럼 결과로 변환한다.") {
                    val result = curriculumService.getCurriculums(USER_ID).first()

                    result.id shouldBe CURRICULUM_ID
                    result.totalCount shouldBe CURRICULUM_SERVICE_TOTAL_COUNT
                    result.solvedCount shouldBe CURRICULUM_SERVICE_SOLVED_COUNT
                }
            }
        }
    }
}
