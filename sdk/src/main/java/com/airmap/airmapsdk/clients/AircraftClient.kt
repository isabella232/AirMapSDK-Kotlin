package com.airmap.airmapsdk.clients

import com.airmap.airmapsdk.models.Manufacturer
import com.serjltt.moshi.adapters.Wrapped
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AircraftClient {
    @GET("manufacturer") @Wrapped(path=["data"])
    fun getManufacturers(): Call<List<Manufacturer>>

    @GET("/manufacturer") @Wrapped(path=["data"])
    fun getManufacturers(@Query("q") name: String): Call<List<Manufacturer>>

    @GET("/model") @Wrapped(path=["data"])
    fun getModel(id: String)
}