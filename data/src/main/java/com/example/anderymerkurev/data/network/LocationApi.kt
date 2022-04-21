package com.example.anderymerkurev.data.network

import com.example.anderymerkurev.domain.entities.Locations
import com.example.anderymerkurev.domain.entities.LocationsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface LocationApi {

    @GET("api/location")
    suspend fun getPageLocations(
        @Query("page") pageNumber: Int,
        @Query("name") name: String,
        @Query("type") type: String,
        @Query("dimension") dimension: String,
    ): Response<LocationsResponse>

    @GET("api/location/{locationSet}")
    suspend fun getListLocations(
        @Path("locationSet") locationSet: String
    ): Response<List<Locations>>

}