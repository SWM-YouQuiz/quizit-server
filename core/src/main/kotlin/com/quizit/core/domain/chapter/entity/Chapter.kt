package com.quizit.core.domain.chapter.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.util.*

@Table
class Chapter(
    @Id
    val id: UUID? = null,
    val courseId: UUID,
    val description: String,
    val document: String,
    val index: Int
)
