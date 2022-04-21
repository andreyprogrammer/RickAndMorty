package com.example.andreymerkurev.rickandmorty.app

import android.app.Application
import com.example.andreymerkurev.rickandmorty.di.Injector

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        Injector.createAppComponent(this)
    }

}