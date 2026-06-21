package com.quizit.core.domain.chapter.repository

import com.quizit.core.common.RepositoryTest
import com.quizit.core.domain.quiz.repository.QuizRepository
import com.quizit.core.domain.user.repository.UserSolvedQuizRepository
import com.quizit.core.fixture.CHAPTER_COURSE_ID
import com.quizit.core.fixture.CHAPTER_DETAIL_RESULT_SIZE
import com.quizit.core.fixture.CHAPTER_SOLVED_QUIZ_COUNT
import com.quizit.core.fixture.CHAPTER_TOTAL_QUIZ_COUNT
import com.quizit.core.fixture.USER_ID
import com.quizit.core.fixture.createChapter
import com.quizit.core.fixture.createQuiz
import com.quizit.core.fixture.createUserSolvedQuiz
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired

class CustomChapterRepositoryTest : RepositoryTest() {
    @Autowired
    private lateinit var chapterRepository: ChapterRepository

    @Autowired
    private lateinit var quizRepository: QuizRepository

    @Autowired
    private lateinit var userSolvedQuizRepository: UserSolvedQuizRepository

    init {
        "findChapterDetails()는 챕터별 퀴즈 수와 사용자 풀이 수를 조회한다." {
            val chapter = chapterRepository.save(createChapter(id = null, courseId = CHAPTER_COURSE_ID))
            val quiz = quizRepository.save(createQuiz(id = null, chapterId = chapter.id!!))
            quizRepository.save(createQuiz(id = null, chapterId = chapter.id!!))
            userSolvedQuizRepository.save(createUserSolvedQuiz(id = null, userId = USER_ID, quizId = quiz.id!!))

            val results = chapterRepository.findChapterDetails(courseId = CHAPTER_COURSE_ID, userId = USER_ID)

            results shouldHaveSize CHAPTER_DETAIL_RESULT_SIZE
            results.first().id shouldBe chapter.id
            results.first().totalCount shouldBe CHAPTER_TOTAL_QUIZ_COUNT
            results.first().solvedCount shouldBe CHAPTER_SOLVED_QUIZ_COUNT
        }
    }
}
