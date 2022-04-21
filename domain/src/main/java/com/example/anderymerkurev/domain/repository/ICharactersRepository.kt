package com.example.anderymerkurev.domain.repository

import com.example.anderymerkurev.domain.entities.Characters
import com.example.anderymerkurev.domain.entities.CharactersResponse
import com.example.anderymerkurev.domain.entities.RequestResult

interface ICharactersRepository {

    suspend fun getPagingData(currentLoadingPageKey: Int, listParams: ArrayList<String>): RequestResult<CharactersResponse>

    suspend fun getCharacters(characterSet: String): RequestResult<List<Characters>>

    suspend fun saveCharacters(response: CharactersResponse)

    suspend fun getLocalPagingData(string: String): RequestResult<CharactersResponse>

}