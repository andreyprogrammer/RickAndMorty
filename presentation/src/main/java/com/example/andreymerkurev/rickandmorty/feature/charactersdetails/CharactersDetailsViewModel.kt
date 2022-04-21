package com.example.andreymerkurev.rickandmorty.feature.charactersdetails

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anderymerkurev.domain.entities.Characters
import com.example.anderymerkurev.domain.entities.Episodes
import com.example.anderymerkurev.domain.entities.RequestResult
import com.example.anderymerkurev.domain.interactors.ICharactersInteractor
import com.example.anderymerkurev.domain.interactors.IEpisodesInteractor
import com.example.andreymerkurev.rickandmorty.di.Injector
import com.example.andreymerkurev.rickandmorty.feature.toElementsSet
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharactersDetailsViewModel @Inject constructor(
    private val charactersInteractor: ICharactersInteractor,
    private val episodesInteractor: IEpisodesInteractor
) : ViewModel() {

    private val _character = MutableLiveData<Characters>().apply {
        value = Characters()
    }
    val character: LiveData<Characters> = _character

    private val _episodes = MutableLiveData<List<Episodes>>().apply {
        value = listOf(Episodes())
    }
    val episodes: LiveData<List<Episodes>> = _episodes

    private val _isLoading = MutableLiveData<Boolean>().apply {
        value = false
    }
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData<Boolean>().apply {
        value = false
    }
    val isError: LiveData<Boolean> = _isError

    fun loadCharacterData(characterId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _isError.value = false
            val charactersResponse = charactersInteractor.getCharacters(characterId)
            when (charactersResponse) {
                is RequestResult.Success -> {
                    val episodeSet = charactersResponse.data[0].episode.toElementsSet()
                    val episodesResponse = episodesInteractor.getEpisodes(episodeSet)
                    when (episodesResponse) {
                        is RequestResult.Success -> {
                            _isError.value = false
                            _episodes.value = episodesResponse.data
                        }
                        is RequestResult.Error -> {
                            _isError.value = true
                        }
                    }

                    _character.value = charactersResponse.data[0]
                }
                is RequestResult.Error -> {
                    Log.e("key01", "Error ${charactersResponse.exception}. Try again later")
                    _isError.value = true
                }
            }
            _isLoading.value = false
        }
    }

    override fun onCleared() {
        super.onCleared()
        Injector.clearCharactersDetailsComponent()
    }
}