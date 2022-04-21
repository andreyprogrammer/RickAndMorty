package com.example.anderymerkurev.data.network

import com.example.anderymerkurev.domain.entities.Characters
import com.example.anderymerkurev.domain.entities.CharactersResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharacterApi {

    @GET("api/character")
    suspend fun getPageCharacters(
        @Query("page") pageNumber: Int,
        @Query("name") name: String,
        @Query("status") status: String,
        @Query("species") species: String,
        @Query("type") type: String,
        @Query("gender") gender: String
    ): Response<CharactersResponse>

    @GET("api/character/{characterSet}")
    suspend fun getListCharacters(
        @Path("characterSet") characterSet: String
    ): Response<List<Characters>>

}