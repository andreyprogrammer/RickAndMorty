package com.example.andreymerkurev.rickandmorty.feature.characterfilter.di

import androidx.lifecycle.ViewModel
import com.example.andreymerkurev.rickandmorty.di.annotation.ViewModelKey
import com.example.andreymerkurev.rickandmorty.feature.characterfilter.CharactersFilterViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
interface CharactersFilterModule {
    @Binds
    @IntoMap
    @ViewModelKey(CharactersFilterViewModel::class)
    fun bindCharactersFilterViewModel(viewModel: CharactersFilterViewModel): ViewModel
}