package com.example.anderymerkurev.data

import com.example.anderymerkurev.data.database.dao.CharacterDao
import com.example.anderymerkurev.data.network.CharacterApi
import com.example.anderymerkurev.data.repository.CharactersRepositoryImpl
import com.example.anderymerkurev.domain.entities.Characters
import io.mockk.*
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import retrofit2.Response


private const val FAKE_PERSON_ID = "777"

@ExtendWith(MockKExtension::class)
class TestCharactersRepository {

    private val responseList = listOf(Characters(id = FAKE_PERSON_ID.toInt()))
    private val response by lazy { Response.success(responseList) }

    @Test
    suspend fun `test when api works and not use dao`() {
        val characterApi = mockk<CharacterApi> {
            coEvery { getListCharacters(FAKE_PERSON_ID)  } returns response
        }

        val characterDao = mockk<CharacterDao>()

        val characterRepository = CharactersRepositoryImpl(characterApi, characterDao)

        characterRepository.getCharacters(FAKE_PERSON_ID)

        coVerify(exactly = 2) {
//            characterRepository.getCharacters(FAKE_PERSON_ID)
            characterApi.getListCharacters(FAKE_PERSON_ID)
            listOf(characterDao) wasNot Called
        }

        confirmVerified(characterRepository)
    }


}