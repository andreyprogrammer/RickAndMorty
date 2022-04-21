package com.example.andreymerkurev.rickandmorty.feature.character.di

import androidx.lifecycle.ViewModel
import com.example.andreymerkurev.rickandmorty.di.annotation.ViewModelKey
import com.example.andreymerkurev.rickandmorty.feature.character.CharactersViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface CharactersModule {
    @Binds
    @IntoMap
    @ViewModelKey(CharactersViewModel::class)
    fun bindCharactersViewModel(viewModel: CharactersViewModel): ViewModel
}