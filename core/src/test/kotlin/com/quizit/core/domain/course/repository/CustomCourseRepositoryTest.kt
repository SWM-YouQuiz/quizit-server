package com.quizit.core.domain.course.repository

import com.quizit.core.common.RepositoryTest
import com.quizit.core.domain.chapter.repository.ChapterRepository
import com.quizit.core.domain.quiz.repository.QuizRepository
import com.quizit.core.domain.user.repository.UserSolvedQuizRepository
import com.quizit.core.fixture.COURSE_CURRICULUM_ID
import com.quizit.core.fixture.COURSE_DETAIL_RESULT_SIZE
import com.quizit.core.fixture.COURSE_SOLVED_QUIZ_COUNT
import com.quizit.core.fixture.COURSE_TOTAL_QUIZ_COUNT
import com.quizit.core.fixture.USER_ID
import com.quizit.core.fixture.createChapter
import com.quizit.core.fixture.createCourse
import com.quizit.core.fixture.createQuiz
import com.quizit.core.fixture.createUserSolvedQuiz
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired

class CustomCourseRepositoryTest : RepositoryTest() {
    @Autowired
    private lateinit var courseRepository: CourseRepository

    @Autowired
    private lateinit var chapterRepository: ChapterRepository

    @Autowired
    private lateinit var quizRepository: QuizRepository

    @Autowired
    private lateinit var userSolvedQuizRepository: UserSolvedQuizRepository

    init {
        "findAllByCurriculumId()는 코스별 퀴즈 수와 사용자 풀이 수를 조회한다." {
            val course = courseRepository.save(createCourse(id = null, curriculumId = COURSE_CURRICULUM_ID))
            val chapter = chapterRepository.save(createChapter(id = null, courseId = course.id!!))
            val quiz = quizRepository.save(createQuiz(id = null, chapterId = chapter.id!!))
            quizRepository.save(createQuiz(id = null, chapterId = chapter.id))
            userSolvedQuizRepository.save(createUserSolvedQuiz(id = null, userId = USER_ID, quizId = quiz.id!!))

            val results =
                courseRepository.findAllByCurriculumId(
                    curriculumId = COURSE_CURRICULUM_ID,
                    userId = USER_ID
                )

            results shouldHaveSize COURSE_DETAIL_RESULT_SIZE
            results.first().id shouldBe course.id
            results.first().totalCount shouldBe COURSE_TOTAL_QUIZ_COUNT
            results.first().solvedCount shouldBe COURSE_SOLVED_QUIZ_COUNT
        }
    }
}
