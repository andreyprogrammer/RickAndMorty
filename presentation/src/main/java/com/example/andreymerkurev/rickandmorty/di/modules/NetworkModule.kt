package com.example.andreymerkurev.rickandmorty.di.modules

import com.example.anderymerkurev.data.network.CharacterApi
import com.example.anderymerkurev.data.network.EpisodeApi
import com.example.anderymerkurev.data.network.LocationApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson) = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    @Singleton
    fun provideGson() = GsonBuilder().create()

    @Provides
    @Singleton
    fun provideCharacterApi(retrofit: Retrofit) = retrofit.create(CharacterApi::class.java)

    @Provides
    @Singleton
    fun provideEpisodeApi(retrofit: Retrofit) = retrofit.create(EpisodeApi::class.java)

    @Provides
    @Singleton
    fun provideLocationApi(retrofit: Retrofit) = retrofit.create(LocationApi::class.java)

    companion object {
        private const val BASE_URL = "https://rickandmortyapi.com/"
    }
}