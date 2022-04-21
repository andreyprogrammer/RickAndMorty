package com.example.andreymerkurev.rickandmorty.feature.locationdetails.di

import androidx.lifecycle.ViewModel
import com.example.andreymerkurev.rickandmorty.di.annotation.ViewModelKey
import com.example.andreymerkurev.rickandmorty.feature.locationdetails.LocationsDetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface LocationsDetailsModule {
    @Binds
    @IntoMap
    @ViewModelKey(LocationsDetailsViewModel::class)
    fun bindLocationsDetailsViewModel(viewModel: LocationsDetailsViewModel): ViewModel
}