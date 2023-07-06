package com.example.tvschedule.domain.show_details.model

import java.time.LocalDate


data class Cast(
    val id: Long,
    val fullName: String,
    val characterName: String,
    val self: Boolean,
    val birthday: LocalDate?,
    val deathday: LocalDate?,
    val birthPlace: String,
    val imageUrl: String
)
