package com.itd.app.core.datetime

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock

fun LocalDateTime.Companion.now(): LocalDateTime {
    return Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
}

fun LocalDateTime.epochSeconds(): Long {
    val instant = toInstant(TimeZone.currentSystemDefault())
    return instant.epochSeconds
}

expect fun LocalDateTime.format(format: String): String