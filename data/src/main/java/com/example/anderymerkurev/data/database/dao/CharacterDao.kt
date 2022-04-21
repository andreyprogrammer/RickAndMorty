package com.example.anderymerkurev.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.anderymerkurev.domain.entities.Characters

@Dao
interface CharacterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCharacters(characters: List<Characters>)

    @Query("SELECT * FROM characters WHERE id=:id")
    suspend fun getCharactersById(id: Int): Characters

    @Query("SELECT * FROM characters WHERE name LIKE '%' || :name || '%'")
    suspend fun getCharactersByName(name: String): List<Characters>

    @Query("SELECT * FROM characters WHERE status LIKE '%' || :status || '%'")
    suspend fun getCharactersByStatus(status: String): List<Characters>

    @Query("SELECT * FROM characters WHERE species LIKE '%' || :species || '%'")
    suspend fun getCharactersBySpecies(species: String): List<Characters>

    @Query("SELECT * FROM characters WHERE type LIKE '%' || :type || '%'")
    suspend fun getCharactersByType(type: String): List<Characters>

    @Query("SELECT * FROM characters WHERE gender LIKE '%' || :gender || '%'")
    suspend fun getCharactersByGender(gender: String): List<Characters>

    @Query("SELECT * FROM characters")
    suspend fun getAllCharacters(): List<Characters>

    @Query("SELECT * FROM characters WHERE name LIKE '%' || :string || '%' " +
            "OR status LIKE '%' || :string || '%' OR species LIKE '%' || :string || '%' " +
            "OR gender LIKE '%' || :string || '%'")
    suspend fun searchCharacters(string: String): List<Characters>

}