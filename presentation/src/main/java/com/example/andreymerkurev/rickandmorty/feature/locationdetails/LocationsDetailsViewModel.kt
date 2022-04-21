package com.example.andreymerkurev.rickandmorty.feature.locationdetails

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anderymerkurev.domain.entities.Characters
import com.example.anderymerkurev.domain.entities.Locations
import com.example.anderymerkurev.domain.entities.RequestResult
import com.example.anderymerkurev.domain.interactors.ICharactersInteractor
import com.example.anderymerkurev.domain.interactors.ILocationsInteractor
import com.example.andreymerkurev.rickandmorty.di.Injector
import com.example.andreymerkurev.rickandmorty.feature.toElementsSet
import kotlinx.coroutines.launch
import javax.inject.Inject

class LocationsDetailsViewModel @Inject constructor(
    private val charactersInteractor: ICharactersInteractor,
    private val locationsInteractor: ILocationsInteractor
) : ViewModel() {

    private val _location = MutableLiveData<Locations>().apply {
        value = Locations()
    }
    val location: LiveData<Locations> = _location

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

    fun loadLocationData(locationId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _isError.value = false
            val locationsResponse = locationsInteractor.getLocations(locationId)
            when (locationsResponse) {
                is RequestResult.Success -> {
                    val characterSet = locationsResponse.data[0].residents.toElementsSet()
                    val charactersResponse = charactersInteractor.getCharacters(characterSet)

                    when (charactersResponse) {
                        is RequestResult.Success -> {
                            _isError.value = false
                            _characters.value = charactersResponse.data
                        }
                        is RequestResult.Error -> {
                            _isError.value = true
                        }
                    }

                    _location.value = locationsResponse.data[0]
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
        Injector.clearLocationsDetailsComponent()
    }
}