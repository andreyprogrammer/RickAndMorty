package com.example.andreymerkurev.rickandmorty.feature.character.di

import com.example.andreymerkurev.rickandmorty.di.annotation.FragmentScope
import com.example.andreymerkurev.rickandmorty.feature.character.CharactersFragment
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [CharactersModule::class])
interface CharactersComponent {
    fun inject(charactersFragment: CharactersFragment)
}