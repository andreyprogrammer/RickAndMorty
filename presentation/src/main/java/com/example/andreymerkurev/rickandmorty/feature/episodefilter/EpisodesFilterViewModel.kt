package com.example.andreymerkurev.rickandmorty.feature.episodefilter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.andreymerkurev.rickandmorty.di.Injector
import javax.inject.Inject

class EpisodesFilterViewModel @Inject constructor() : ViewModel() {

    private val _name = MutableLiveData<String>().apply {
        value = ""
    }
    val name: LiveData<String> = _name

    private val _episode = MutableLiveData<String>().apply {
        value = ""
    }
    val episode: LiveData<String> = _episode

    fun setName(name: String) {
        _name.value = name
    }

    fun setEpisode(type: String) {
        _episode.value = type
    }

    override fun onCleared() {
        super.onCleared()
        Injector.clearEpisodesFilterComponent()
    }
}