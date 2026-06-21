package com.quizit.core.domain.quiz.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.util.*

@Table("quiz")
class Quiz(
    @Id
    var id: UUID? = null,
    val chapterId: UUID,
    val question: String,
    val solution: String
)
