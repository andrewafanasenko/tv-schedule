package com.example.tvschedule.presentation.common

import java.time.LocalDate
import java.time.format.DateTimeFormatter


fun LocalDate?.getPersonLifeRange(birthPlace: String, deathday: LocalDate?): String {
    this ?: return ""
    val formatter = DateTimeFormatter.ofPattern("d MMM yyyy")
    val place = if (birthPlace.isNotBlank()) " ($birthPlace)" else ""
    deathday ?: return this.format(formatter) + place
    return "${this.format(formatter)} $place - ${deathday.format(formatter)}"
}

fun LocalDate?.getSeasonDateRange(endDate: LocalDate?): String {
    this ?: return ""
    val formatter = DateTimeFormatter.ofPattern("d MMM yyyy")
    endDate ?: return format(formatter)
    return "${this.format(formatter)} - ${endDate.format(formatter)}"
}
