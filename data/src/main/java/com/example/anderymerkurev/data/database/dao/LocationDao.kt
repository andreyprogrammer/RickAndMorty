package com.example.anderymerkurev.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.anderymerkurev.domain.entities.Locations

@Dao
interface LocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveLocations(locations: List<Locations>)

    @Query("SELECT * FROM locations WHERE id =:id")
    suspend fun getLocationsById(id: Int): Locations

    @Query("SELECT * FROM locations")
    suspend fun getAllLocations(): List<Locations>

    @Query("SELECT * FROM locations WHERE name LIKE '%' || :name || '%'")
    suspend fun getLocationsByName(name: String): List<Locations>

    @Query("SELECT * FROM locations WHERE type LIKE '%' || :type || '%'")
    suspend fun getLocationsByType(type: String): List<Locations>

    @Query("SELECT * FROM locations WHERE dimension LIKE '%' || :dimension || '%'")
    suspend fun getLocationsByDimension(dimension: String): List<Locations>

    @Query("SELECT * FROM locations WHERE name LIKE '%' || :string || '%' " +
            "OR type LIKE '%' || :string || '%' OR dimension LIKE '%' || :string || '%'")
    suspend fun searchLocations(string: String): List<Locations>

}