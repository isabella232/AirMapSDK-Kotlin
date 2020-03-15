package com.airmap.airmapsdk.clients

import com.airmap.airmapsdk.Response
import com.airmap.airmapsdk.models.Airspace
import com.serjltt.moshi.adapters.Wrapped
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AirspaceClient {
    @GET("{id}")
    @Wrapped(path = ["data"])
    fun getAirspace(@Path("id") id: String): Response<Airspace>

    @GET("list")
    @Wrapped(path = ["data"])
    fun getAirspaces(@Query("ids", encoded = true) ids: String): Response<List<Airspace>>
}
