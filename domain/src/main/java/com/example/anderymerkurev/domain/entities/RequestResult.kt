package com.example.anderymerkurev.domain.entities

sealed class RequestResult<out T : Any> {

    data class Success<out T : Any>(val data: T) : RequestResult<T>()
    data class Error<out T : Any>(val exception: String) : RequestResult<T>()

}