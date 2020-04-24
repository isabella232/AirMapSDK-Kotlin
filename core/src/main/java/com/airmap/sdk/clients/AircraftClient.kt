package com.airmap.sdk.clients

import com.airmap.sdk.models.Manufacturer
import com.airmap.sdk.models.Model
import com.airmap.sdk.networking.AirMapCall
import com.serjltt.moshi.adapters.Wrapped
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AircraftClient {
    @GET("manufacturer")
    @Wrapped(path = ["data"])
    fun getManufacturers(
        @Query("q") name: String? = null,
    ): AirMapCall<List<Manufacturer>>

    @GET("model")
    @Wrapped(path = ["data"])
    fun getModels(
        @Query("q") name: String? = null,
        @Query("manufacturer") manufacturerId: String? = null,
    ): AirMapCall<List<Model>>

    @GET("model/{id}")
    @Wrapped(path = ["data"])
    fun getModel(
        @Path("id") id: String
    ): AirMapCall<Model>
}
