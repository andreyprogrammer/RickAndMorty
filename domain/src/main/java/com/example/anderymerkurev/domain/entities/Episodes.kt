package com.example.anderymerkurev.domain.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName

@Entity(tableName = "episodes")
@TypeConverters(DBTypeConverters::class)
data class Episodes(
    @PrimaryKey(autoGenerate = false)
    val id: Int = 0,
    val name: String = "-",
    @SerializedName("air_date")
    val airDate: String = "-",
    val episode: String = "-",
    val characters: List<String> = listOf(),
    val url: String = "",
    val created: String = "-"
)