package com.quizit.core.domain.curriculum.service

import com.quizit.core.domain.curriculum.dto.result.GetCurriculumResult
import com.quizit.core.domain.curriculum.repository.CurriculumRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class CurriculumService(
    private val curriculumRepository: CurriculumRepository
) {
    @Transactional(readOnly = true)
    fun getCurriculums(userId: UUID): List<GetCurriculumResult> =
        curriculumRepository.findCurriculumDetails(userId)
            .map { GetCurriculumResult.from(it) }
}
