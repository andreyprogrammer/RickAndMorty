package com.example.andreymerkurev.rickandmorty.feature.episodefilter.di

import androidx.lifecycle.ViewModel
import com.example.andreymerkurev.rickandmorty.di.annotation.ViewModelKey
import com.example.andreymerkurev.rickandmorty.feature.episodefilter.EpisodesFilterViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface EpisodesFilterModule {
    @Binds
    @IntoMap
    @ViewModelKey(EpisodesFilterViewModel::class)
    fun bindEpisodesFilterViewModel(viewModel: EpisodesFilterViewModel): ViewModel
}