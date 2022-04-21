package com.example.anderymerkurev.domain.repository

import com.example.anderymerkurev.domain.entities.Locations
import com.example.anderymerkurev.domain.entities.LocationsResponse
import com.example.anderymerkurev.domain.entities.RequestResult

interface ILocationsRepository {

    suspend fun getPagingData(currentLoadingPageKey: Int, listParams: ArrayList<String>): RequestResult<LocationsResponse>

    suspend fun getLocations(locationSet: String): RequestResult<List<Locations>>

    suspend fun saveLocations(response: LocationsResponse)

    suspend fun getLocalPagingData(string: String): RequestResult<LocationsResponse>

}