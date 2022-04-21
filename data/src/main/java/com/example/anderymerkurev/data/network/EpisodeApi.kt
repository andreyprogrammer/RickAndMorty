package com.example.anderymerkurev.data.network

import com.example.anderymerkurev.domain.entities.Episodes
import com.example.anderymerkurev.domain.entities.EpisodesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface EpisodeApi {

    @GET("api/episode")
    suspend fun getPageEpisodes(
        @Query("page") pageNumber: Int,
        @Query("name") name: String,
        @Query("episode") episode: String,
    ): Response<EpisodesResponse>

    @GET("api/episode/{episodeSet}")
    suspend fun getListEpisodes(
        @Path("episodeSet") episodeSet: String
    ): Response<List<Episodes>>

}