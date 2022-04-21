package com.example.anderymerkurev.domain.interactors

import com.example.anderymerkurev.domain.entities.*
import com.example.anderymerkurev.domain.repository.IEpisodesRepository
import javax.inject.Inject

interface IEpisodesInteractor {

    suspend fun getPagingData(
        currentLoadingPageKey: Int,
        listParams: ArrayList<String>
    ): RequestResult<EpisodesResponse>

    suspend fun getEpisodes(episodeSet: String): RequestResult<List<Episodes>>

    suspend fun saveEpisodes(response: EpisodesResponse)

    suspend fun getLocalPagingData(string: String): RequestResult<EpisodesResponse>
}

class EpisodesInteractor @Inject constructor(
    private val episodesRepository: IEpisodesRepository
) : IEpisodesInteractor {

    override suspend fun getPagingData(
        currentLoadingPageKey: Int,
        listParams: ArrayList<String>
    ): RequestResult<EpisodesResponse> =
        episodesRepository.getPagingData(currentLoadingPageKey, listParams)

    override suspend fun getEpisodes(episodeSet: String): RequestResult<List<Episodes>> =
        episodesRepository.getEpisodes(episodeSet)

    override suspend fun saveEpisodes(response: EpisodesResponse) {
        episodesRepository.saveEpisodes(response)
    }

    override suspend fun getLocalPagingData(string: String) =
        episodesRepository.getLocalPagingData(string)

}