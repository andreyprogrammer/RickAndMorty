package com.example.andreymerkurev.rickandmorty.feature.episodefilter.di

import com.example.andreymerkurev.rickandmorty.di.annotation.FragmentScope
import com.example.andreymerkurev.rickandmorty.feature.episodefilter.EpisodesFilterFragment
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [EpisodesFilterModule::class])
interface EpisodesFilterComponent {
    fun inject(episodesFilterFragment: EpisodesFilterFragment)
}