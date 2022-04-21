package com.example.anderymerkurev.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.anderymerkurev.data.database.dao.CharacterDao
import com.example.anderymerkurev.data.database.dao.EpisodeDao
import com.example.anderymerkurev.data.database.dao.LocationDao
import com.example.anderymerkurev.domain.entities.Characters
import com.example.anderymerkurev.domain.entities.Episodes
import com.example.anderymerkurev.domain.entities.Locations
import javax.inject.Singleton

@Singleton
@Database(entities = [Characters::class, Episodes::class, Locations::class], version = 1, exportSchema = false)
abstract class RickAndMortyDatabase : RoomDatabase() {

    abstract val characterDao: CharacterDao
    abstract val episodeDao: EpisodeDao
    abstract val locationDao: LocationDao

    companion object {
        @Volatile
        private var INSTANCE: RickAndMortyDatabase? = null
        fun getInstance(context: Context): RickAndMortyDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        RickAndMortyDatabase::class.java,
                        "rick_and_morty_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}