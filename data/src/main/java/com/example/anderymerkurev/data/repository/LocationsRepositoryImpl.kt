package com.example.anderymerkurev.data.repository

import com.example.anderymerkurev.data.database.dao.LocationDao
import com.example.anderymerkurev.data.isValuesEmpty
import com.example.anderymerkurev.data.network.LocationApi
import com.example.anderymerkurev.data.toListId
import com.example.anderymerkurev.domain.entities.Info
import com.example.anderymerkurev.domain.entities.Locations
import com.example.anderymerkurev.domain.entities.LocationsResponse
import com.example.anderymerkurev.domain.entities.RequestResult
import com.example.anderymerkurev.domain.repository.ILocationsRepository
import javax.inject.Inject

class LocationsRepositoryImpl @Inject constructor(
    private val locationApi: LocationApi,
    private val locationDao: LocationDao
) : ILocationsRepository {

    override suspend fun getPagingData(
        currentLoadingPageKey: Int,
        listParams: ArrayList<String>
    ): RequestResult<LocationsResponse> {
        try {
            val remoteResponse = locationApi.getPageLocations(
                currentLoadingPageKey,
                name = listParams[0],
                type = listParams[1],
                dimension = listParams[2],
            )
            if (remoteResponse.isSuccessful) {
                val data = remoteResponse.body()
                saveLocations(data!!)
                return RequestResult.Success(data)
            } else {
                val localResponse = if (listParams.isValuesEmpty()) {
                    loadAllLocalLocations()
                } else {
                    loadLocalLocationsByParams(listParams)
                }
                return convertRequestResult(localResponse, "${remoteResponse.code()}")
            }
        } catch (e: Exception) {
            val localResponse = if (listParams.isValuesEmpty()) {
                loadAllLocalLocations()
            } else {
                loadLocalLocationsByParams(listParams)
            }
            return convertRequestResult(localResponse, "$e")
        }
    }

    private fun convertRequestResult(
        response: RequestResult<List<Locations>>,
        errorText: String
    ): RequestResult<LocationsResponse> {
        return when (response) {
            is RequestResult.Success -> {
                RequestResult.Success(
                    LocationsResponse(
                        Info(response.data.size, 0, "", ""),
                        response.data
                    )
                )
            }
            is RequestResult.Error -> {
                RequestResult.Error(errorText)
            }
        }
    }

    override suspend fun getLocations(locationSet: String): RequestResult<List<Locations>> {
        try {
            val remoteResponse = loadRemoteLocations(locationSet) as List<Locations>
            return RequestResult.Success(remoteResponse)
        } catch (e: Exception) {
            return loadLocalLocationsByIDs(locationSet)
        }
    }

    override suspend fun saveLocations(response: LocationsResponse) {
        val list = response.results
        locationDao.saveLocations(list)
    }

    override suspend fun getLocalPagingData(string: String): RequestResult<LocationsResponse> {
        return convertRequestResult(
            try {
                val response = locationDao.searchLocations(string)
                RequestResult.Success(response)
            } catch (e: Exception) {
                return RequestResult.Error("$e")
            }, "No data"
        )
    }

    private suspend fun loadRemoteLocations(locationSet: String) =
        locationApi.getListLocations(locationSet).body()

    private suspend fun loadLocalLocationsByIDs(locationSet: String): RequestResult<List<Locations>> {
        val locationsList = mutableListOf<Locations>()
        return try {
            locationSet.toListId().forEach {
                locationsList.add(locationDao.getLocationsById(it))
            }
            RequestResult.Success(locationsList)
        } catch (e: Exception) {
            RequestResult.Error("$e")
        }
    }

    private suspend fun loadAllLocalLocations(): RequestResult<List<Locations>> {
        return try {
            RequestResult.Success(locationDao.getAllLocations())
        } catch (e: Exception) {
            RequestResult.Error("$e")
        }
    }

    private suspend fun loadLocalLocationsByParams(
        listParams: ArrayList<String>
    ): RequestResult<List<Locations>> {

        val nameList = mutableListOf<Locations>()
        val typeList = mutableListOf<Locations>()
        val dimensionList = mutableListOf<Locations>()
        val totalList: MutableList<MutableList<Locations>> = mutableListOf()

        try {
            if (listParams[0].isNotBlank() && listParams[0].isNotEmpty()) {
                nameList.addAll(locationDao.getLocationsByName(listParams[0]))
                totalList.add(nameList)
            }
            if (listParams[1].isNotBlank() && listParams[1].isNotEmpty()) {
                typeList.addAll(locationDao.getLocationsByType(listParams[1]))
                totalList.add(typeList)
            }
            if (listParams[2].isNotBlank() && listParams[2].isNotEmpty()) {
                dimensionList.addAll(locationDao.getLocationsByDimension(listParams[2]))
                totalList.add(dimensionList)
            }
            if (totalList.size > 1) {
                for (i in 1 until totalList.size) {
                    totalList[0].retainAll(totalList[i])
                }
            }
            return if (totalList.isNotEmpty())
                RequestResult.Success(totalList[0])
            else
                RequestResult.Error("Empty list")
        } catch (e: Exception) {
            return RequestResult.Error("$e")
        }
    }
}