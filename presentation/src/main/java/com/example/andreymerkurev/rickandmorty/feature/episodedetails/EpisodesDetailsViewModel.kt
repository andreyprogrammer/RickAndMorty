package com.example.andreymerkurev.rickandmorty.feature.episodedetails

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

class EpisodesDetailsViewModel @Inject constructor(
    private val charactersInteractor: ICharactersInteractor,
    private val episodesInteractor: IEpisodesInteractor
) : ViewModel() {

    private val _episode = MutableLiveData<Episodes>().apply {
        value = Episodes()
    }
    val episode: LiveData<Episodes> = _episode

    private val _characters = MutableLiveData<List<Characters>>().apply {
        value = listOf(Characters())
    }
    val characters: LiveData<List<Characters>> = _characters

    private val _isLoading = MutableLiveData<Boolean>().apply {
        value = false
    }
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData<Boolean>().apply {
        value = false
    }
    val isError: LiveData<Boolean> = _isError

    fun loadEpisodeData(episodeId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _isError.value = false
            val episodesResponse = episodesInteractor.getEpisodes(episodeId)
            when (episodesResponse) {
                is RequestResult.Success -> {
                    val characterSet = episodesResponse.data[0].characters.toElementsSet()
                    val charactersResponse = charactersInteractor.getCharacters(characterSet)

                    when (charactersResponse) {
                        is RequestResult.Success -> {
                            _isError.value = false
                            _characters.value = charactersResponse.data
                        }
                        is RequestResult.Error -> {
                            Log.e("key01", "Error ${charactersResponse.exception}")
                            _isError.value = true
                        }
                    }

                    _episode.value = episodesResponse.data[0]
                }
                is RequestResult.Error -> {
                    _isError.value = true
                }
            }
            _isLoading.value = false
        }
    }

    override fun onCleared() {
        super.onCleared()
        Injector.clearEpisodesDetailsComponent()
    }
}