package com.example.tvschedule.data.db

import androidx.room.TypeConverter


class Converters {
    @TypeConverter
    fun toListOfStrings(flatStringList: String): List<String> {
        return if (flatStringList.isNotBlank()) flatStringList.split(",") else emptyList()
    }
    @TypeConverter
    fun fromListOfStrings(listOfString: List<String>): String {
        return listOfString.joinToString(",")
    }
}
