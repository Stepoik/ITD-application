package com.itd.app.features.common.ui

import androidx.compose.runtime.Composable
import com.itd.app.core.datetime.epochSeconds
import com.itd.app.core.datetime.now
import itd.composeapp.generated.resources.Res
import itd.composeapp.generated.resources.days_ago
import itd.composeapp.generated.resources.hours_ago
import itd.composeapp.generated.resources.minutes_ago
import kotlinx.datetime.LocalDateTime
import org.jetbrains.compose.resources.pluralStringResource


@Composable
fun LocalDateTime.toAgoText(): String {
    val diffSeconds = LocalDateTime.now().epochSeconds() - this.epochSeconds()
    if (diffSeconds <= 0) return "только что"

    val minutes = diffSeconds / 60
    val hours = diffSeconds / 3600
    val days = diffSeconds / 86_400

    return when {
        minutes < 1 -> "только что"
        minutes < 60 -> pluralStringResource(Res.plurals.minutes_ago, minutes.toInt(), minutes)
        hours < 24 -> pluralStringResource(Res.plurals.hours_ago, hours.toInt(), hours)
        days < 7 -> pluralStringResource(Res.plurals.days_ago, days.toInt(), days)
        else -> pluralStringResource(Res.plurals.days_ago, days.toInt(), days)
    }
}