package com.example.andreymerkurev.rickandmorty.feature.characterfilter.di

import com.example.andreymerkurev.rickandmorty.di.annotation.FragmentScope
import com.example.andreymerkurev.rickandmorty.feature.characterfilter.CharactersFilterFragment
import dagger.Subcomponent


@FragmentScope
@Subcomponent(modules = [CharactersFilterModule::class])
interface CharactersFilterComponent {
    fun inject(charactersFilterFragment: CharactersFilterFragment)
}