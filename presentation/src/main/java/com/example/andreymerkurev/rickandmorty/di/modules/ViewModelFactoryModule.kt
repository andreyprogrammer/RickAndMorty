package com.example.andreymerkurev.rickandmorty.di.modules

import androidx.lifecycle.ViewModelProvider
import com.example.andreymerkurev.rickandmorty.di.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
interface ViewModelFactoryModule {

    @Binds
    fun bindsViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

}