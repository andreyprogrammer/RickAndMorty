package com.example.andreymerkurev.rickandmorty.feature.locationfilter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.andreymerkurev.rickandmorty.di.Injector
import javax.inject.Inject

class LocationsFilterViewModel @Inject constructor() : ViewModel() {

    private val _name = MutableLiveData<String>().apply {
        value = ""
    }
    val name: LiveData<String> = _name

    private val _type = MutableLiveData<String>().apply {
        value = ""
    }
    val type: LiveData<String> = _type

    private val _dimension = MutableLiveData<String>().apply {
        value = ""
    }
    val dimension: LiveData<String> = _dimension

    fun setName(name: String) {
        _name.value = name
    }

    fun setType(type: String) {
        _type.value = type
    }

    fun setDimension(type: String) {
        _dimension.value = type
    }

    override fun onCleared() {
        super.onCleared()
        Injector.clearLocationsFilterComponent()
    }
}