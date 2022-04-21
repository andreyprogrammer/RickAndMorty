package com.example.andreymerkurev.rickandmorty.feature.locationfilter.di

import androidx.lifecycle.ViewModel
import com.example.andreymerkurev.rickandmorty.di.annotation.ViewModelKey
import com.example.andreymerkurev.rickandmorty.feature.locationfilter.LocationsFilterViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface LocationsFilterModule {
    @Binds
    @IntoMap
    @ViewModelKey(LocationsFilterViewModel::class)
    fun bindLocationsFilterViewModel(viewModel: LocationsFilterViewModel): ViewModel
}