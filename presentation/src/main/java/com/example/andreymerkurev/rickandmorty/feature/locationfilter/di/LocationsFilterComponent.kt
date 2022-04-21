package com.example.andreymerkurev.rickandmorty.feature.locationfilter.di

import com.example.andreymerkurev.rickandmorty.di.annotation.FragmentScope
import com.example.andreymerkurev.rickandmorty.feature.locationfilter.LocationsFilterFragment
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [LocationsFilterModule::class])
interface LocationsFilterComponent {
    fun inject(locationsFilterFragment: LocationsFilterFragment)
}