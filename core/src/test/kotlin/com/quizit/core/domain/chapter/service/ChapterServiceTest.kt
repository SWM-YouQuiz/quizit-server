package com.quizit.core.domain.chapter.service

import com.quizit.core.domain.chapter.repository.ChapterRepository
import com.quizit.core.fixture.CHAPTER_COURSE_ID
import com.quizit.core.fixture.CHAPTER_ID
import com.quizit.core.fixture.CHAPTER_SERVICE_SOLVED_COUNT
import com.quizit.core.fixture.CHAPTER_SERVICE_TOTAL_COUNT
import com.quizit.core.fixture.USER_ID
import com.quizit.core.fixture.createChapterDetailProjections
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

class ChapterServiceTest : BehaviorSpec() {
    override fun isolationMode(): IsolationMode = IsolationMode.InstancePerLeaf

    private val chapterRepository = mockk<ChapterRepository>()
    private val chapterService = ChapterService(chapterRepository)

    init {
        given("getChaptersByCourseId()는") {
            every {
                chapterRepository.findChapterDetails(courseId = CHAPTER_COURSE_ID, userId = USER_ID)
            } returns createChapterDetailProjections()

            `when`("챕터 상세 projection을 조회하면") {
                then("챕터 결과로 변환한다.") {
                    val result =
                        chapterService.getChaptersByCourseId(
                            courseId = CHAPTER_COURSE_ID,
                            userId = USER_ID
                        ).first()

                    result.id shouldBe CHAPTER_ID
                    result.totalCount shouldBe CHAPTER_SERVICE_TOTAL_COUNT
                    result.solvedCount shouldBe CHAPTER_SERVICE_SOLVED_COUNT
                }
            }
        }
    }
}
