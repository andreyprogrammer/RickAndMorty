package com.example.anderymerkurev.domain.interactors

import com.example.anderymerkurev.domain.entities.Characters
import com.example.anderymerkurev.domain.entities.CharactersResponse
import com.example.anderymerkurev.domain.entities.RequestResult
import com.example.anderymerkurev.domain.repository.ICharactersRepository
import javax.inject.Inject

interface ICharactersInteractor {

    suspend fun getPagingData(
        currentLoadingPageKey: Int,
        listParams: ArrayList<String>
    ): RequestResult<CharactersResponse>

    suspend fun getCharacters(characterSet: String): RequestResult<List<Characters>>

    suspend fun saveCharacters(response: CharactersResponse)

    suspend fun getLocalPagingData(string: String): RequestResult<CharactersResponse>
}

class CharactersInteractor @Inject constructor(
    private val charactersRepository: ICharactersRepository
) : ICharactersInteractor {

    override suspend fun getPagingData(
        currentLoadingPageKey: Int,
        listParams: ArrayList<String>
    ): RequestResult<CharactersResponse> =
        charactersRepository.getPagingData(currentLoadingPageKey, listParams)

    override suspend fun getCharacters(characterSet: String): RequestResult<List<Characters>> =
        charactersRepository.getCharacters(characterSet)

    override suspend fun saveCharacters(response: CharactersResponse) {
        charactersRepository.saveCharacters(response)
    }

    override suspend fun getLocalPagingData(string: String) =
        charactersRepository.getLocalPagingData(string)

}