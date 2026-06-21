package com.quizit.core.domain.course.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.util.*

@Table("course")
class Course(
    @Id
    val id: UUID? = null,
    val curriculumId: UUID,
    val title: String
)
