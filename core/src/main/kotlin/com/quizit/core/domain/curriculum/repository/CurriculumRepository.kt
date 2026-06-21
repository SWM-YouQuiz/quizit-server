package com.quizit.core.domain.curriculum.repository

import com.quizit.core.domain.curriculum.entity.Curriculum
import com.quizit.core.global.repository.JdbcRepository
import java.util.*

interface CurriculumRepository :
    JdbcRepository<Curriculum, UUID>,
    CustomCurriculumRepository
