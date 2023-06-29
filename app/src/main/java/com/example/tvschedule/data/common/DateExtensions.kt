package com.example.tvschedule.data.common

import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneId

fun String?.toLocalDateTime(): LocalDateTime? = try {
    val utcDateTime = OffsetDateTime.parse(this)
    utcDateTime
        .withOffsetSameInstant(
            ZoneId.systemDefault().rules.getOffset(utcDateTime.toLocalDateTime())
        )
        .toLocalDateTime()
} catch (e: Exception) {
    null
}
