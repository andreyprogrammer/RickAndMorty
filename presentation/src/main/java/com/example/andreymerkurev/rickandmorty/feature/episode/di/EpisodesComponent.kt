package com.example.andreymerkurev.rickandmorty.feature.episode.di

import com.example.andreymerkurev.rickandmorty.di.annotation.FragmentScope
import com.example.andreymerkurev.rickandmorty.feature.episode.EpisodesFragment
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [EpisodesModule::class])
interface EpisodesComponent {
    fun inject(episodesFragment: EpisodesFragment)
}