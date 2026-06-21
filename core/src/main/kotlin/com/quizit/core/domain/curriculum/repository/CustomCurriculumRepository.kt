package com.quizit.core.domain.curriculum.repository

import com.quizit.core.domain.curriculum.dto.projection.CurriculumDetailProjection
import java.util.*

interface CustomCurriculumRepository {
    fun findCurriculumDetails(userId: UUID): List<CurriculumDetailProjection>
}
