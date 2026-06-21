package com.quizit.core.domain.curriculum.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.util.*

@Table("curriculum")
class Curriculum(
    @Id var id: UUID? = null,
    val title: String,
    val imageUrl: String
)
