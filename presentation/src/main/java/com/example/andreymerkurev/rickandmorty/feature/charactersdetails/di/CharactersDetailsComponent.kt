package com.example.andreymerkurev.rickandmorty.feature.charactersdetails.di

import com.example.andreymerkurev.rickandmorty.di.annotation.FragmentScope
import com.example.andreymerkurev.rickandmorty.feature.charactersdetails.CharactersDetailsFragment
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [CharactersDetailsModule::class])
interface CharactersDetailsComponent {
    fun inject(charactersDetailsFragment: CharactersDetailsFragment)
}