package com.airmap.airmapsdk.clients

import com.airmap.airmapsdk.Response
import com.airmap.airmapsdk.models.Airspace
import com.serjltt.moshi.adapters.Wrapped
import retrofit2.http.GET
import retrofit2.http.Path

interface AirspaceClient {
    @GET("{id}")
    @Wrapped(path = ["data"])
    fun getAirspace(@Path("id") id: String): Response<Airspace>

    // TODO: Will this need the use of a new annotation (e.g. @CommaSeperated) and use of a ConverterFactory (for converting String list to comma separated String)
    @GET("{ids}")
    fun getAirspaces(@Path("ids") ids: List<String>): Response<List<Airspace>>
}
