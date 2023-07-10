package com.example.tvschedule.data.common

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import java.time.LocalDate
import java.time.LocalDateTime


class DateExtensionsTest {

    private val validDateTimeString = "2007-12-03T10:15:30+01:00"
    private val validDateString = "2007-12-03"

    @Test
    fun `string to LocalDateTime returns valid LocalDateTime`() {
        assertThat(validDateTimeString.toLocalDateTime())
            .isInstanceOf(LocalDateTime::class.java)
    }

    @Test
    fun `string to LocalDateTime returns null`() {
        assertThat(validDateString.toLocalDateTime())
            .isEqualTo(null)
    }

    @Test
    fun `string to LocalDate returns valid LocalDate`() {
        assertThat(validDateString.toLocalDate())
            .isInstanceOf(LocalDate::class.java)
    }

    @Test
    fun `string to LocalDate returns null`() {
        assertThat(validDateTimeString.toLocalDate())
            .isEqualTo(null)
    }
}
