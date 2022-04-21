package com.example.andreymerkurev.rickandmorty.di.component

import android.content.Context
import com.example.andreymerkurev.rickandmorty.di.modules.AppModule
import com.example.andreymerkurev.rickandmorty.di.modules.NetworkModule
import com.example.andreymerkurev.rickandmorty.di.modules.ViewModelFactoryModule
import com.example.andreymerkurev.rickandmorty.feature.character.di.CharactersComponent
import com.example.andreymerkurev.rickandmorty.feature.characterfilter.di.CharactersFilterComponent
import com.example.andreymerkurev.rickandmorty.feature.charactersdetails.di.CharactersDetailsComponent
import com.example.andreymerkurev.rickandmorty.feature.episode.di.EpisodesComponent
import com.example.andreymerkurev.rickandmorty.feature.episodedetails.di.EpisodesDetailsComponent
import com.example.andreymerkurev.rickandmorty.feature.episodefilter.di.EpisodesFilterComponent
import com.example.andreymerkurev.rickandmorty.feature.location.di.LocationsComponent
import com.example.andreymerkurev.rickandmorty.feature.locationdetails.di.LocationsDetailsComponent
import com.example.andreymerkurev.rickandmorty.feature.locationfilter.di.LocationsFilterComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class, ViewModelFactoryModule::class])
interface AppComponent {

    val context: Context
    val charactersComponent: CharactersComponent
    val charactersDetailsComponent: CharactersDetailsComponent
    val locationsComponent: LocationsComponent
    val locationsDetailsComponent: LocationsDetailsComponent
    val episodesComponent: EpisodesComponent
    val episodesDetailsComponent: EpisodesDetailsComponent
    val charactersFilterComponent: CharactersFilterComponent
    val episodesFilterComponent: EpisodesFilterComponent
    val locationsFilterComponent: LocationsFilterComponent
}