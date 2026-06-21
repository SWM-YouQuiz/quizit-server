package com.quizit.core.domain.chapter.repository

import com.quizit.core.domain.chapter.entity.Chapter
import com.quizit.core.global.repository.JdbcRepository
import java.util.UUID

interface ChapterRepository :
    JdbcRepository<Chapter, UUID>,
    CustomChapterRepository
