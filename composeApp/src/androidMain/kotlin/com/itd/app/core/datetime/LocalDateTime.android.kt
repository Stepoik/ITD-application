package com.itd.app.core.datetime

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

actual fun LocalDateTime.format(format: String): String {
    val millis = toInstant(TimeZone.UTC).toEpochMilliseconds()
    val date = Date(millis)

    val formatter = SimpleDateFormat(format, Locale.getDefault())

    return formatter.format(date)
}