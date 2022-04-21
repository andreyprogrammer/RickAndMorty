package com.example.andreymerkurev.rickandmorty.feature.characterfilter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.anderymerkurev.domain.entities.Gender
import com.example.anderymerkurev.domain.entities.Status
import com.example.andreymerkurev.rickandmorty.di.Injector
import javax.inject.Inject

class CharactersFilterViewModel @Inject constructor() : ViewModel() {

    private val _name = MutableLiveData<String>().apply {
        value = ""
    }
    val name: LiveData<String> = _name

    private val _status = MutableLiveData<Status>().apply {
        value = Status.NONE
    }
    val status: LiveData<Status> = _status

    private val _species = MutableLiveData<String>().apply {
        value = ""
    }
    val species: LiveData<String> = _species

    private val _type = MutableLiveData<String>().apply {
        value = ""
    }
    val type: LiveData<String> = _type

    private val _gender = MutableLiveData<Gender>().apply {
        value = Gender.NONE
    }
    val gender: LiveData<Gender> = _gender

    fun setName(name: String) {
        _name.value = name
    }

    fun setSpecies(species: String) {
        _species.value = species
    }

    fun setType(type: String) {
        _type.value = type
    }

    fun setStatus(status: Status) {
        _status.value = status
    }

    fun setGender(gender: Gender) {
        _gender.value = gender
    }

    override fun onCleared() {
        super.onCleared()
        Injector.clearCharactersFilterComponent()
    }
}