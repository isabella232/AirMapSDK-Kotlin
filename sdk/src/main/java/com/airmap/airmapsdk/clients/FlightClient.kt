package com.airmap.airmapsdk.clients

import com.airmap.airmapsdk.AirMap
import com.airmap.airmapsdk.Response
import com.airmap.airmapsdk.models.Comm
import com.airmap.airmapsdk.models.Flight
import com.airmap.airmapsdk.models.Geometry
import com.serjltt.moshi.adapters.Wrapped
import retrofit2.http.*
import java.util.*

interface FlightClient {
    /**
     * TODO: Copy description from https://developers.airmap.com/reference
     * TODO: This method returns a paged response. Create a class to wrap+expose the paged response
     *
     * @param pilotId
     * @param startAfter
     * @param startBefore
     * @param endAfter
     * @param endBefore
     * @param country
     * @param state
     * @param city
     * @param enhance
     * @param limit
     * @return
     */
    @GET("/")
    @Wrapped(path = ["data", "results"])
    fun getFlights(
        @Query("pilot_id") pilotId: String? = null,
        @Query("start_before") startBefore: Date? = null,
        @Query("start_after") startAfter: Date? = null,
        @Query("end_before") endBefore: Date? = null,
        @Query("end_after") endAfter: Date? = null,
        @Query("country") country: String? = null,
        @Query("state") state: String? = null,
        @Query("city") city: String? = null,
        @Query("geometry") geometry: Geometry? = null,
        @Query("enhance") enhance: Boolean? = null,
        @Query("limit") limit: Int? = null
    ): Response<List<Flight>>

    @GET("{id}")
    @Wrapped(path = ["data"])
    fun getFlight(@Path("id") flightId: String, @Query("enhance") enhance: Boolean = false): Response<Flight>

    @POST("/point")
    @Wrapped(path = ["data"])
    fun createFlightPoint(@Body flight: Flight): Response<Flight>

    @POST("/path")
    @Wrapped(path = ["data"])
    fun createFlightPath(@Body flight: Flight): Response<Flight>

    @POST("/polygon")
    @Wrapped(path = ["data"])
    fun createFlightPolygon(@Body flight: Flight): Response<Flight>

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
