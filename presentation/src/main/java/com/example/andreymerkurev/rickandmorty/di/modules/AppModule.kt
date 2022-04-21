package com.example.andreymerkurev.rickandmorty.di.modules

import android.content.Context
import com.example.anderymerkurev.data.database.RickAndMortyDatabase
import com.example.anderymerkurev.data.database.dao.CharacterDao
import com.example.anderymerkurev.data.database.dao.EpisodeDao
import com.example.anderymerkurev.data.database.dao.LocationDao
import com.example.anderymerkurev.data.network.CharacterApi
import com.example.anderymerkurev.data.network.EpisodeApi
import com.example.anderymerkurev.data.network.LocationApi
import com.example.anderymerkurev.data.network.PicassoLoader
import com.example.anderymerkurev.data.repository.CharactersRepositoryImpl
import com.example.anderymerkurev.data.repository.EpisodesRepositoryImpl
import com.example.anderymerkurev.data.repository.LocationsRepositoryImpl
import com.example.anderymerkurev.domain.interactors.*
import com.example.anderymerkurev.domain.repository.ICharactersRepository
import com.example.anderymerkurev.domain.repository.IEpisodesRepository
import com.example.anderymerkurev.domain.repository.ILocationsRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideContext() = context

    @Singleton
    @Provides
    fun provideCharacterDao(): CharacterDao =
        RickAndMortyDatabase.getInstance(context).characterDao

    @Singleton
    @Provides
    fun provideEpisodeDao(): EpisodeDao =
        RickAndMortyDatabase.getInstance(context).episodeDao

    @Singleton
    @Provides
    fun provideLocationDao(): LocationDao =
        RickAndMortyDatabase.getInstance(context).locationDao

    @Singleton
    @Provides
    fun provideCharactersRepository(
        characterApi: CharacterApi,
        characterDao: CharacterDao
    ): ICharactersRepository =
        CharactersRepositoryImpl(characterApi, characterDao)

    @Singleton
    @Provides
    fun provideEpisodesRepository(
        episodeApi: EpisodeApi,
        episodeDao: EpisodeDao
    ): IEpisodesRepository =
        EpisodesRepositoryImpl(episodeApi, episodeDao)

    @Singleton
    @Provides
    fun provideLocationsRepository(
        locationApi: LocationApi,
        locationDao: LocationDao
    ): ILocationsRepository =
        LocationsRepositoryImpl(locationApi, locationDao)

    @Singleton
    @Provides
    fun provideCharactersInteractor(charactersRepository: ICharactersRepository): ICharactersInteractor =
        CharactersInteractor(charactersRepository)

    @Singleton
    @Provides
    fun provideEpisodesInteractor(episodesRepository: IEpisodesRepository): IEpisodesInteractor =
        EpisodesInteractor(episodesRepository)

    @Singleton
    @Provides
    fun provideLocationsInteractor(locationsRepository: ILocationsRepository): ILocationsInteractor =
        LocationsInteractor(locationsRepository)

    @Singleton
    @Provides
    fun providePicasso(): PicassoLoader = PicassoLoader()

}