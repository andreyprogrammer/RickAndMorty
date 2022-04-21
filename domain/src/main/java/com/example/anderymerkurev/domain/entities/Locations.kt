package com.example.anderymerkurev.domain.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "locations")
@TypeConverters(DBTypeConverters::class)
data class Locations(
    @PrimaryKey(autoGenerate = false)
    val id: Int = 0,
    val name: String = "-",
    val type: String = "-",
    val dimension: String = "-",
    val residents: List<String> = listOf(),
    val url: String = "",
    val created: String = "-"
)