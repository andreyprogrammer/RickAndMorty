package com.example.anderymerkurev.domain.repository

import com.example.anderymerkurev.domain.entities.Episodes
import com.example.anderymerkurev.domain.entities.EpisodesResponse
import com.example.anderymerkurev.domain.entities.RequestResult

interface IEpisodesRepository {

    suspend fun getPagingData(currentLoadingPageKey: Int, listParams: ArrayList<String>): RequestResult<EpisodesResponse>

    suspend fun getEpisodes(episodeSet: String): RequestResult<List<Episodes>>

    suspend fun saveEpisodes(response: EpisodesResponse)

    suspend fun getLocalPagingData(string: String): RequestResult<EpisodesResponse>


}