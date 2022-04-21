package com.example.andreymerkurev.rickandmorty.feature.location.di

import com.example.andreymerkurev.rickandmorty.di.annotation.FragmentScope
import com.example.andreymerkurev.rickandmorty.feature.location.LocationsFragment
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [LocationsModule::class])
interface LocationsComponent {
    fun inject(locationsFragment: LocationsFragment)
}