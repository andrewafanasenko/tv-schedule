package com.example.tvschedule.data.common

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private const val DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ssX"

fun String?.toLocalDateTime(): LocalDateTime? = try {
    val dateFormatter = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN)
    LocalDateTime.parse(this, dateFormatter)
} catch (e: Exception) {
    null
}
