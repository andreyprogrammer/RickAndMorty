package com.example.anderymerkurev.data.repository

import com.example.anderymerkurev.data.database.dao.EpisodeDao
import com.example.anderymerkurev.data.isValuesEmpty
import com.example.anderymerkurev.data.network.EpisodeApi
import com.example.anderymerkurev.data.toListId
import com.example.anderymerkurev.domain.entities.Episodes
import com.example.anderymerkurev.domain.entities.EpisodesResponse
import com.example.anderymerkurev.domain.entities.Info
import com.example.anderymerkurev.domain.entities.RequestResult
import com.example.anderymerkurev.domain.repository.IEpisodesRepository
import javax.inject.Inject

class EpisodesRepositoryImpl @Inject constructor(
    private val episodeApi: EpisodeApi,
    private val episodeDao: EpisodeDao
) : IEpisodesRepository {

    override suspend fun getPagingData(
        currentLoadingPageKey: Int,
        listParams: ArrayList<String>
    ): RequestResult<EpisodesResponse> {
        try {
            val remoteResponse = episodeApi.getPageEpisodes(
                currentLoadingPageKey,
                name = listParams[0],
                episode = listParams[1]
            )
            if (remoteResponse.isSuccessful) {
                val data = remoteResponse.body()
                saveEpisodes(data!!)
                return RequestResult.Success(data)
            } else {
                val localResponse = if (listParams.isValuesEmpty()) {
                    loadAllLocalEpisodes()
                } else {
                    loadLocalEpisodesByParams(listParams)
                }
                return convertRequestResult(localResponse, "${remoteResponse.code()}")
            }
        } catch (e: Exception) {
            val localResponse = if (listParams.isValuesEmpty()) {
                loadAllLocalEpisodes()
            } else {
                loadLocalEpisodesByParams(listParams)
            }
            return convertRequestResult(localResponse, "$e")
        }
    }

    private fun convertRequestResult(
        response: RequestResult<List<Episodes>>,
        errorText: String
    ): RequestResult<EpisodesResponse> {
        return when (response) {
            is RequestResult.Success -> {
                RequestResult.Success(
                    EpisodesResponse(
                        Info(response.data.size, 0, "", ""),
                        response.data
                    )
                )
            }
            is RequestResult.Error -> {
                RequestResult.Error(errorText)
            }
        }
    }

    override suspend fun getEpisodes(episodeSet: String): RequestResult<List<Episodes>> {
        try {
            val remoteResponse = loadRemoteEpisodes(episodeSet) as List<Episodes>
            return RequestResult.Success(remoteResponse)
        } catch (e: Exception) {
            return loadLocalEpisodesByIDs(episodeSet)
        }
    }

    override suspend fun saveEpisodes(response: EpisodesResponse) {
        val list = response.results
        episodeDao.saveEpisodes(list)
    }

    override suspend fun getLocalPagingData(string: String): RequestResult<EpisodesResponse> {
        return convertRequestResult(
            try {
                val response = episodeDao.searchEpisodes(string)
                RequestResult.Success(response)
            } catch (e: Exception) {
                return RequestResult.Error("$e")
            }, "No data"
        )
    }

    private suspend fun loadRemoteEpisodes(episodeSet: String) =
        episodeApi.getListEpisodes(episodeSet).body()

    private suspend fun loadLocalEpisodesByIDs(episodeSet: String): RequestResult<List<Episodes>> {
        val episodesList = mutableListOf<Episodes>()
        return try {
            episodeSet.toListId().forEach {
                episodesList.add(episodeDao.getEpisodeById(it))
            }
            RequestResult.Success(episodesList)
        } catch (e: Exception) {
            RequestResult.Error("$e")
        }
    }

    private suspend fun loadAllLocalEpisodes(): RequestResult<List<Episodes>> {
        return try {
            RequestResult.Success(episodeDao.getAllEpisodes())
        } catch (e: Exception) {
            RequestResult.Error("$e")
        }
    }

    private suspend fun loadLocalEpisodesByParams(
        listParams: ArrayList<String>
    ): RequestResult<List<Episodes>> {

        val nameList = mutableListOf<Episodes>()
        val episodeList = mutableListOf<Episodes>()
        val totalList: MutableList<MutableList<Episodes>> = mutableListOf()

        try {
            if (listParams[0].isNotBlank() && listParams[0].isNotEmpty()) {
                nameList.addAll(episodeDao.getEpisodesByName(listParams[0]))
                totalList.add(nameList)
            }
            if (listParams[1].isNotBlank() && listParams[1].isNotEmpty()) {
                episodeList.addAll(episodeDao.getEpisodesByEpisode(listParams[1]))
                totalList.add(episodeList)
            }
            if (totalList.size > 1) {
                for (i in 1 until totalList.size) {
                    totalList[0].retainAll(totalList[i])
                }
            }
            return if (totalList.isNotEmpty())
                RequestResult.Success(totalList[0])
            else
                RequestResult.Error("Empty list")
        } catch (e: Exception) {
            return RequestResult.Error("$e")
        }

    }
}