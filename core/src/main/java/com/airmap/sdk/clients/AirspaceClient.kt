package com.airmap.sdk.clients

import com.airmap.sdk.models.Airspace
import com.airmap.sdk.networking.AirMapCall
import com.airmap.sdk.networking.CommaSeparated
import com.serjltt.moshi.adapters.Wrapped
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AirspaceClient {
    /**
     * Get an [Airspace] by [id]
     */
    @GET("{id}")
    @Wrapped(path = ["data"])
    fun getAirspace(
        @Path("id") id: String,
    ): AirMapCall<Airspace>

    /**
     * Get [Airspace]s specified by [ids]
     */
    @GET("list")
    @Wrapped(path = ["data"])
    fun getAirspaces(
        @CommaSeparated @Query("ids", encoded = true) ids: List<String>,
    ): AirMapCall<List<Airspace>>

    // TODO
    //  @GET("search")
    //  @Wrapped(path = ["data"])
    //  fun searchAirspace(
    //  ): AirMapCall<List<Airspace>>
}
