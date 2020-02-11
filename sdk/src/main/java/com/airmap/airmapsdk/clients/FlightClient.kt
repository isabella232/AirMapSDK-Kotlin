package com.airmap.airmapsdk.clients

import com.airmap.airmapsdk.Response
import com.airmap.airmapsdk.models.*
import com.aungkyawpaing.geoshi.model.Geometry
import com.serjltt.moshi.adapters.Wrapped
import retrofit2.http.*
import retrofit2.http.Path
import java.util.*

typealias FlightFeatureValue = Any // TODO

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
    @GET("")
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
    fun getFlight(
        @Path("id") flightId: String,
        @Query("enhance") enhance: Boolean = false
    ): Response<Flight>

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
    fun getFlightPlan(@Path("id") flightId: String): Response<FlightPlan>

    @POST("plan")
    @Wrapped(path = ["data"])
    fun createFlightPlan(
        pilotId: String,
        geometry: Geometry,
        takeoffLatitude: Double,
        takeoffLongitude: Double,
        maxAltitudeAgl: Double,
        startTime: Date,
        endTime: Date,
        buffer: Int,
        flightDescription: String,
        rulesets: List<String>? = null,
        flightFeatures: List<FlightFeatureValue>? = null
    ): Response<FlightPlan>

    @PATCH("plan/{id}")
    @Wrapped(path = ["data"])
    fun updateFlightPlan(
        @Path("id") flightPlanId: String,
        geometry: Geometry? = null,
        takeoffLatitude: Double? = null,
        takeoffLongitude: Double? = null,
        maxAltitudeAgl: Double? = null,
        startTime: Date? = null,
        endTime: Date? = null,
        buffer: Int? = null,
        flightDescription: String? = null,
        rulesets: List<String>? = null,
        flightFeatures: List<FlightFeatureValue>? = null
    ): Response<FlightPlan>

    @POST("plan/{id}/submit")
    @FormUrlEncoded // TODO: test if this works (not sure if API accepts form url encoded format)
    @Wrapped(path = ["data"])
    fun submitFlightPlan(@Path("id") flightPlanId: String, @Field("public") isPublic: Boolean)

    @GET("plan/{id}/briefing")
    @Wrapped(path = ["data"])
    fun getFlightBriefing(@Path("id") flightPlanId: String): Response<FlightBriefing>
}
