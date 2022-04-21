package com.example.anderymerkurev.domain.interactors

import com.example.anderymerkurev.domain.entities.*
import com.example.anderymerkurev.domain.repository.ILocationsRepository
import javax.inject.Inject

interface ILocationsInteractor {

    suspend fun getPagingData(
        currentLoadingPageKey: Int,
        listParams: ArrayList<String>
    ): RequestResult<LocationsResponse>

    suspend fun getLocations(locationSet: String): RequestResult<List<Locations>>

    suspend fun saveLocations(response: LocationsResponse)

    suspend fun getLocalPagingData(string: String): RequestResult<LocationsResponse>

}

class LocationsInteractor @Inject constructor(
    private val locationsRepository: ILocationsRepository
) : ILocationsInteractor {

    override suspend fun getPagingData(
        currentLoadingPageKey: Int,
        listParams: ArrayList<String>
    ): RequestResult<LocationsResponse> =
        locationsRepository.getPagingData(currentLoadingPageKey, listParams)

    override suspend fun getLocations(locationSet: String): RequestResult<List<Locations>> =
        locationsRepository.getLocations(locationSet)

    override suspend fun saveLocations(response: LocationsResponse) {
        locationsRepository.saveLocations(response)
    }

    override suspend fun getLocalPagingData(string: String) =
        locationsRepository.getLocalPagingData(string)

}