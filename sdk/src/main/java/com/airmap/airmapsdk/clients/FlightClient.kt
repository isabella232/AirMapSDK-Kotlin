package com.airmap.airmapsdk.clients

import com.airmap.airmapsdk.AirMap
import com.airmap.airmapsdk.Response
import com.airmap.airmapsdk.models.Flight
import com.serjltt.moshi.adapters.Wrapped
import retrofit2.http.GET
import retrofit2.http.Path

interface FlightClient {
    @GET("{id}/aircraft")
    @Wrapped(path = ["data"])
    fun getFlights(@Path("id") pilotId: String = AirMap.userId): Response<List<Flight>>
}
