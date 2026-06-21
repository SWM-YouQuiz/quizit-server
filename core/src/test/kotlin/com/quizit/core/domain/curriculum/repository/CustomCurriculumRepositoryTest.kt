package com.quizit.core.domain.curriculum.repository

import com.quizit.core.common.RepositoryTest
import com.quizit.core.domain.chapter.repository.ChapterRepository
import com.quizit.core.domain.course.repository.CourseRepository
import com.quizit.core.domain.quiz.repository.QuizRepository
import com.quizit.core.domain.user.repository.UserSolvedQuizRepository
import com.quizit.core.fixture.CURRICULUM_DETAIL_RESULT_SIZE
import com.quizit.core.fixture.CURRICULUM_SOLVED_QUIZ_COUNT
import com.quizit.core.fixture.CURRICULUM_TOTAL_QUIZ_COUNT
import com.quizit.core.fixture.USER_ID
import com.quizit.core.fixture.createChapter
import com.quizit.core.fixture.createCourse
import com.quizit.core.fixture.createCurriculum
import com.quizit.core.fixture.createQuiz
import com.quizit.core.fixture.createUserSolvedQuiz
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired

class CustomCurriculumRepositoryTest : RepositoryTest() {
    @Autowired
    private lateinit var curriculumRepository: CurriculumRepository

    @Autowired
    private lateinit var courseRepository: CourseRepository

    @Autowired
    private lateinit var chapterRepository: ChapterRepository

    @Autowired
    private lateinit var quizRepository: QuizRepository

    @Autowired
    private lateinit var userSolvedQuizRepository: UserSolvedQuizRepository

    init {
        "findCurriculumDetails()는 커리큘럼별 퀴즈 수와 사용자 풀이 수를 조회한다." {
            val curriculum = curriculumRepository.save(createCurriculum(id = null))
            val course = courseRepository.save(createCourse(id = null, curriculumId = curriculum.id!!))
            val chapter = chapterRepository.save(createChapter(id = null, courseId = course.id!!))
            val quiz = quizRepository.save(createQuiz(id = null, chapterId = chapter.id!!))
            quizRepository.save(createQuiz(id = null, chapterId = chapter.id!!))
            userSolvedQuizRepository.save(createUserSolvedQuiz(id = null, userId = USER_ID, quizId = quiz.id!!))

            val results = curriculumRepository.findCurriculumDetails(userId = USER_ID)

            results shouldHaveSize CURRICULUM_DETAIL_RESULT_SIZE
            results.first().id shouldBe curriculum.id
            results.first().totalCount shouldBe CURRICULUM_TOTAL_QUIZ_COUNT
            results.first().solvedCount shouldBe CURRICULUM_SOLVED_QUIZ_COUNT
        }
    }
}
