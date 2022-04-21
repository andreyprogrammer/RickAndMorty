package com.example.anderymerkurev.data.repository

import com.example.anderymerkurev.data.database.dao.CharacterDao
import com.example.anderymerkurev.data.isValuesEmpty
import com.example.anderymerkurev.data.network.CharacterApi
import com.example.anderymerkurev.data.toListId
import com.example.anderymerkurev.domain.entities.Characters
import com.example.anderymerkurev.domain.entities.CharactersResponse
import com.example.anderymerkurev.domain.entities.Info
import com.example.anderymerkurev.domain.entities.RequestResult
import com.example.anderymerkurev.domain.repository.ICharactersRepository
import javax.inject.Inject

class CharactersRepositoryImpl @Inject constructor(
    private val characterApi: CharacterApi,
    private val characterDao: CharacterDao
) : ICharactersRepository {

    override suspend fun getPagingData(
        currentLoadingPageKey: Int,
        listParams: ArrayList<String>
    ): RequestResult<CharactersResponse> {
        try {
            val remoteResponse = characterApi.getPageCharacters(
                currentLoadingPageKey,
                name = listParams[0],
                status = listParams[1],
                species = listParams[2],
                type = listParams[3],
                gender = listParams[4],
            )
            if (remoteResponse.isSuccessful) {
                val data = remoteResponse.body()
                saveCharacters(data!!)
                return RequestResult.Success(data)
            } else {
                val localResponse = if (listParams.isValuesEmpty()) {
                    loadAllLocalCharacters()
                } else {
                    loadLocalCharactersByParams(listParams)
                }
                return convertRequestResult(localResponse, "${remoteResponse.code()}")
            }
        } catch (e: Exception) {
            val localResponse = if (listParams.isValuesEmpty()) {
                    loadAllLocalCharacters()
            } else {
                loadLocalCharactersByParams(listParams)
            }
            return convertRequestResult(localResponse, "$e")
        }
    }

    private fun convertRequestResult(
        response: RequestResult<List<Characters>>,
        errorText: String
    ): RequestResult<CharactersResponse> {
        return when (response) {
            is RequestResult.Success -> {
                RequestResult.Success(
                    CharactersResponse(
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

    override suspend fun getCharacters(characterSet: String): RequestResult<List<Characters>> {
        try {
            val remoteResponse = loadRemoteCharacters(characterSet) as List<Characters>
            return RequestResult.Success(remoteResponse)
        } catch (e: Exception) {
            return loadLocalCharactersByIDs(characterSet)
        }
    }

    override suspend fun saveCharacters(response: CharactersResponse) {
        val list = response.results
        characterDao.saveCharacters(list)
    }

    override suspend fun getLocalPagingData(string: String): RequestResult<CharactersResponse> {
        return convertRequestResult(
            try {
                val response = characterDao.searchCharacters(string)
                RequestResult.Success(response)
            } catch (e: Exception) {
                return RequestResult.Error("$e")
            }, "No data"
        )
    }

    private suspend fun loadRemoteCharacters(characterSet: String) =
        characterApi.getListCharacters(characterSet).body()

    private suspend fun loadLocalCharactersByIDs(
        characterSet: String
    ): RequestResult<List<Characters>> {
        val charactersList = mutableListOf<Characters>()
        return try {
            characterSet.toListId().forEach {
                charactersList.add(characterDao.getCharactersById(it))
            }
            RequestResult.Success(charactersList)
        } catch (e: Exception) {
            RequestResult.Error("$e")
        }
    }

    private suspend fun loadAllLocalCharacters(): RequestResult<List<Characters>> {
        return try {
            RequestResult.Success(characterDao.getAllCharacters())
        } catch (e: Exception) {
            RequestResult.Error("$e")
        }
    }

    private suspend fun loadLocalCharactersByParams(
        listParams: ArrayList<String>
    ): RequestResult<List<Characters>> {

        val nameList = mutableListOf<Characters>()
        val statusList = mutableListOf<Characters>()
        val speciesList = mutableListOf<Characters>()
        val typeList = mutableListOf<Characters>()
        val genderList = mutableListOf<Characters>()
        val totalList: MutableList<MutableList<Characters>> = mutableListOf()

        try {
            if (listParams[0].isNotBlank() && listParams[0].isNotEmpty()) {
                nameList.addAll(characterDao.getCharactersByName(listParams[0]))
                totalList.add(nameList)
            }
            if (listParams[1].isNotBlank() && listParams[1].isNotEmpty()) {
                statusList.addAll(characterDao.getCharactersByStatus(listParams[1]))
                totalList.add(statusList)
            }
            if (listParams[2].isNotBlank() && listParams[2].isNotEmpty()) {
                speciesList.addAll(characterDao.getCharactersBySpecies(listParams[2]))
                totalList.add(speciesList)
            }
            if (listParams[3].isNotBlank() && listParams[3].isNotEmpty()) {
                typeList.addAll(characterDao.getCharactersByType(listParams[3]))
                totalList.add(typeList)
            }
            if (listParams[4].isNotBlank() && listParams[4].isNotEmpty()) {
                genderList.addAll(characterDao.getCharactersByGender(listParams[4]))
                totalList.add(genderList)
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