package com.quizit.core.global.extension

import com.fasterxml.uuid.Generators
import java.util.*

fun createUuid(): UUID =
    Generators.timeBasedGenerator()
        .generate()
