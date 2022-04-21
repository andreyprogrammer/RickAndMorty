package com.example.andreymerkurev.rickandmorty.feature.episodedetails.di

import com.example.andreymerkurev.rickandmorty.di.annotation.FragmentScope
import com.example.andreymerkurev.rickandmorty.feature.episodedetails.EpisodesDetailsFragment
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [EpisodesDetailsModule::class])
interface EpisodesDetailsComponent {
    fun inject(episodesDetailsFragment: EpisodesDetailsFragment)
}