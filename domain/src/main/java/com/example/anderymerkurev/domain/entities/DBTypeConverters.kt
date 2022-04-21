package com.example.anderymerkurev.domain.entities

import androidx.room.TypeConverter

class DBTypeConverters {
    @TypeConverter
    fun fromList(list: List<String>): String {
        return if (list.isNotEmpty())
            list.joinToString(",", prefix = "", postfix = "")
        else
            ""
    }

    @TypeConverter
    fun toList(input: String): List<String> {
        return if (input.isNotEmpty()) input.split(",").map { it.trim() }
        else
            listOf<String>()
    }
}