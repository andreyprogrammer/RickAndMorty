package com.example.andreymerkurev.rickandmorty.feature.episodedetails.di

import androidx.lifecycle.ViewModel
import com.example.andreymerkurev.rickandmorty.di.annotation.ViewModelKey
import com.example.andreymerkurev.rickandmorty.feature.episodedetails.EpisodesDetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface EpisodesDetailsModule {
    @Binds
    @IntoMap
    @ViewModelKey(EpisodesDetailsViewModel::class)
    fun bindEpisodesDetailsViewModel(viewModel: EpisodesDetailsViewModel): ViewModel
}