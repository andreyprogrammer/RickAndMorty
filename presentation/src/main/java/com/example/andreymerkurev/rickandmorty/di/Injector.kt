package com.example.andreymerkurev.rickandmorty.di

import android.content.Context
import com.example.andreymerkurev.rickandmorty.di.component.AppComponent
import com.example.andreymerkurev.rickandmorty.di.component.DaggerAppComponent
import com.example.andreymerkurev.rickandmorty.di.modules.AppModule
import com.example.andreymerkurev.rickandmorty.feature.character.di.CharactersComponent
import com.example.andreymerkurev.rickandmorty.feature.characterfilter.di.CharactersFilterComponent
import com.example.andreymerkurev.rickandmorty.feature.charactersdetails.di.CharactersDetailsComponent
import com.example.andreymerkurev.rickandmorty.feature.episode.di.EpisodesComponent
import com.example.andreymerkurev.rickandmorty.feature.episodedetails.di.EpisodesDetailsComponent
import com.example.andreymerkurev.rickandmorty.feature.episodefilter.di.EpisodesFilterComponent
import com.example.andreymerkurev.rickandmorty.feature.location.di.LocationsComponent
import com.example.andreymerkurev.rickandmorty.feature.locationdetails.di.LocationsDetailsComponent
import com.example.andreymerkurev.rickandmorty.feature.locationfilter.di.LocationsFilterComponent

object Injector {

    private lateinit var appComponent: AppComponent

    private var charactersComponent: CharactersComponent? = null
    private var locationsComponent: LocationsComponent? = null
    private var episodesComponent: EpisodesComponent? = null
    private var charactersDetailsComponent: CharactersDetailsComponent? = null
    private var locationsDetailsComponent: LocationsDetailsComponent? = null
    private var episodesDetailsComponent: EpisodesDetailsComponent? = null
    private var charactersFilterComponent: CharactersFilterComponent? = null
    private var episodesFilterComponent: EpisodesFilterComponent? = null
    private var locationsFilterComponent: LocationsFilterComponent? = null

    fun createAppComponent(context: Context) {
        appComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule(context))
            .build()
    }

    fun getCharacterComponent(): CharactersComponent {
        if (charactersComponent == null) {
            charactersComponent = appComponent.charactersComponent
        }
        return charactersComponent!!
    }

    fun getCharactersDetailsComponent(): CharactersDetailsComponent {
        if (charactersDetailsComponent == null) {
            charactersDetailsComponent = appComponent.charactersDetailsComponent
        }
        return charactersDetailsComponent!!
    }

    fun getLocationsComponent(): LocationsComponent {
        if (locationsComponent == null) {
            locationsComponent = appComponent.locationsComponent
        }
        return locationsComponent!!
    }

    fun getLocationsDetailsComponent(): LocationsDetailsComponent {
        if (locationsDetailsComponent == null) {
            locationsDetailsComponent = appComponent.locationsDetailsComponent
        }
        return locationsDetailsComponent!!
    }

    fun getEpisodesComponent(): EpisodesComponent {
        if (episodesComponent == null) {
            episodesComponent = appComponent.episodesComponent
        }
        return episodesComponent!!
    }

    fun getEpisodesDetailsComponent(): EpisodesDetailsComponent {
        if (episodesDetailsComponent == null) {
            episodesDetailsComponent = appComponent.episodesDetailsComponent
        }
        return episodesDetailsComponent!!
    }

    fun getCharactersFilterComponent(): CharactersFilterComponent {
        if (charactersFilterComponent == null) {
            charactersFilterComponent = appComponent.charactersFilterComponent
        }
        return charactersFilterComponent!!
    }

    fun getEpisodesFilterComponent(): EpisodesFilterComponent {
        if (episodesFilterComponent == null) {
            episodesFilterComponent = appComponent.episodesFilterComponent
        }
        return episodesFilterComponent!!
    }

    fun getLocationsFilterComponent(): LocationsFilterComponent {
        if (locationsFilterComponent == null) {
            locationsFilterComponent = appComponent.locationsFilterComponent
        }
        return locationsFilterComponent!!
    }

    fun clearCharactersComponent() {
        charactersComponent = null
    }

    fun clearLocationsComponent() {
        locationsComponent = null
    }

    fun clearEpisodesComponent() {
        episodesComponent = null
    }

    fun clearCharactersDetailsComponent() {
        charactersDetailsComponent = null
    }

    fun clearLocationsDetailsComponent() {
        locationsDetailsComponent = null
    }

    fun clearEpisodesDetailsComponent() {
        episodesDetailsComponent = null
    }

    fun clearCharactersFilterComponent() {
        charactersFilterComponent = null
    }

    fun clearEpisodesFilterComponent() {
        episodesFilterComponent = null
    }

    fun clearLocationsFilterComponent() {
        locationsFilterComponent = null
    }
}