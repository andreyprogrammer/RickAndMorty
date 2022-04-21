package com.example.anderymerkurev.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.anderymerkurev.domain.entities.Episodes

@Dao
interface EpisodeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveEpisodes(episodes: List<Episodes>)

    @Query("SELECT * FROM episodes WHERE id =:id")
    suspend fun getEpisodeById(id: Int): Episodes

    @Query("SELECT * FROM episodes")
    suspend fun getAllEpisodes(): List<Episodes>

    @Query("SELECT * FROM episodes WHERE name LIKE '%' || :name || '%'")
    suspend fun getEpisodesByName(name: String): List<Episodes>

    @Query("SELECT * FROM episodes WHERE episode LIKE '%' || :episode || '%'")
    suspend fun getEpisodesByEpisode(episode: String): List<Episodes>

    @Query("SELECT * FROM episodes WHERE name LIKE '%' || :string || '%' " +
            "OR episode LIKE '%' || :string || '%'")
    suspend fun searchEpisodes(string: String): List<Episodes>

}