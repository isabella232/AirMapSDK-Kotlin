package com.airmap.sdk.clients

import com.airmap.sdk.models.Airspace
import com.airmap.sdk.networking.AirMapCall
import com.serjltt.moshi.adapters.Wrapped
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AirspaceClient {
    @GET("{id}")
    @Wrapped(path = ["data"])
    fun getAirspace(
        @Path("id") id: String,
    ): AirMapCall<Airspace>

    @GET("list")
    @Wrapped(path = ["data"])
    fun getAirspaces(
        @Query("ids", encoded = true) ids: String,
    ): AirMapCall<List<Airspace>>
}
