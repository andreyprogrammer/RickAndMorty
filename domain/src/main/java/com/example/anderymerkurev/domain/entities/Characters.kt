package com.example.anderymerkurev.domain.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "characters")
@TypeConverters(DBTypeConverters::class)
data class Characters(
    @PrimaryKey(autoGenerate = false)
    val id: Int = 0,
    val name: String = "-",
    val status: String = "-",
    val species: String = "-",
    val type: String = "-",
    val gender: String = "-",
    @Embedded(prefix = "origin")
    val origin: Origin = Origin("-", ""),
    @Embedded(prefix = "location")
    val location: Locations = Locations(0, ""),
    val image: String = "-",
    val episode: List<String> = listOf("-"),
    val url: String = "-",
    val created: String = "-"
)