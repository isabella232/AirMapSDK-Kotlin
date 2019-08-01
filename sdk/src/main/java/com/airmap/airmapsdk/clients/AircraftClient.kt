package com.airmap.airmapsdk.clients

import com.airmap.airmapsdk.models.Manufacturer
import com.airmap.airmapsdk.models.Model
import com.serjltt.moshi.adapters.Wrapped
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AircraftClient {
    @GET("manufacturer") @Wrapped(path=["data"])
    fun getManufacturers(@Query("q") name: String? = null): Call<List<Manufacturer>>

    @GET("model") @Wrapped(path=["data"])
    fun getModels(@Query("q") name: String? = null, @Query("manufacturer") manufacturerId: String? = null): Call<List<Model>>

    @GET("model/{id}") @Wrapped(path=["data"])
    fun getModel(@Path("id") id: String): Call<Model>
}
