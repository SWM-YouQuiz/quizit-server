package com.quizit.core.domain.quiz.repository

import com.quizit.core.domain.quiz.entity.Quiz
import com.quizit.core.global.repository.JdbcRepository
import java.util.*

interface QuizRepository :
    JdbcRepository<Quiz, UUID>,
    CustomQuizRepository
