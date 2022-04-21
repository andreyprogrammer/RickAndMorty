package com.example.andreymerkurev.rickandmorty.feature.charactersdetails.di

import androidx.lifecycle.ViewModel
import com.example.andreymerkurev.rickandmorty.di.annotation.ViewModelKey
import com.example.andreymerkurev.rickandmorty.feature.charactersdetails.CharactersDetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface CharactersDetailsModule {
    @Binds
    @IntoMap
    @ViewModelKey(CharactersDetailsViewModel::class)
    fun bindCharactersDetailsViewModel(viewModel: CharactersDetailsViewModel): ViewModel
}