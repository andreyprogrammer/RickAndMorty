package com.example.andreymerkurev.rickandmorty.feature.locationdetails.di

import com.example.andreymerkurev.rickandmorty.di.annotation.FragmentScope
import com.example.andreymerkurev.rickandmorty.feature.locationdetails.LocationsDetailsFragment
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [LocationsDetailsModule::class])
interface LocationsDetailsComponent {
    fun inject(locationsDetailsFragment: LocationsDetailsFragment)
}