package com.example.tvschedule.data.db

import com.google.common.truth.Truth.assertThat
import org.junit.Test


class ConvertersTest {

    private val converters = Converters()
    private val valuesString = "1,2,3,4,5"
    private val listOfValues = listOf("1", "2", "3", "4", "5")

    @Test
    fun `string to list of strings returns valid list`() {
        val result = converters.toListOfStrings(valuesString)
        assertThat(result).isEqualTo(listOfValues)
    }

    @Test
    fun `list of strings to string returns valid string`() {
        val result = converters.fromListOfStrings(listOfValues)
        assertThat(result).isEqualTo(valuesString)
    }
}
