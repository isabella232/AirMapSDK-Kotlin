package com.airmap.airmapsdk.clients

import com.airmap.airmapsdk.AirMap
import com.airmap.airmapsdk.Response
import com.airmap.airmapsdk.models.Comm
import com.airmap.airmapsdk.models.Flight
import com.serjltt.moshi.adapters.Wrapped
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface FlightClient {
//    @GET("flight/{id}")
//    @Wrapped(path = ["data"])
//    fun getFlights(@Path("id") pilotId: String = AirMap.userId.orEmpty()): Response<List<Flight>>

    // getFlights

    @GET("{id}")
    @Wrapped(path = ["data"])
    fun getFlight(@Path("id") flightId: String, @Query("enhance") enhance: Boolean = false): Response<Flight>

    // TODO: express input as raw params instead of as Flight
    @POST("/point")
    @Wrapped(path = ["data"])
    fun createFlightPoint(flight: Flight): Response<Flight>

    // TODO: express input as raw params instead of as Flight
    @POST("/path")
    @Wrapped(path = ["data"])
    fun createFlightPath(flight: Flight): Response<Flight>

    // TODO: express input as raw params instead of as Flight
    @POST("/polygon")
    @Wrapped(path = ["data"])
    fun createFlightPolygon(flight: Flight): Response<Flight>

    @POST("{id}/end")
    @Wrapped(path = ["data"])
    fun endFlight(@Path("id") flightId: String): Response<Flight>

    @POST("{id}/delete")
    @Wrapped(path = ["data"])
    fun deleteFlight(@Path("id") flightId: String): Response<Unit>

    @POST("{id}/start-comm")
    @Wrapped(path = ["data"])
    fun startComm(@Path("id") flightId: String): Response<Comm>

    @POST("{id}/end-comm")
    @Wrapped(path = ["data"])
    fun endComm(@Path("id") flightId: String): Response<Unit>

    @GET("{id}/plan")
    @Wrapped(path = ["data"])
    fun getFlightPlan(@Path("id") flightId: String)
}
