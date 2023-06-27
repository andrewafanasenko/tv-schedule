package com.example.tvschedule.data.common

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


fun String?.toLocalDateTime(): LocalDateTime {
    return try {
        val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssX")
        LocalDateTime.parse(this, dateFormatter)
    } catch (e: Exception) {
        LocalDateTime.now()
    }
}
