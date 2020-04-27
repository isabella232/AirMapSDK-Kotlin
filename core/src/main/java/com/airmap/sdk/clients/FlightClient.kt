package com.airmap.sdk.clients

import com.airmap.sdk.models.Comm
import com.airmap.sdk.models.Flight
import com.airmap.sdk.models.FlightBriefing
import com.airmap.sdk.models.FlightPlan
import com.airmap.sdk.networking.AirMapCall
import com.airmap.sdk.networking.CommaSeparated
import com.aungkyawpaing.geoshi.model.Geometry
import com.serjltt.moshi.adapters.Wrapped
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.Date

interface FlightClient {
    /**
     * Query all public [Flight]s, which can be filtered by certain parameters.
     *
     * @param pilotId Limit flights to this pilot
     * @param startAfter Return flights starting after this time
     * @param startBefore Return flights starting before this time
     * @param endAfter Return flights ending after this time
     * @param endBefore Return flights ending before this time
     * @param country Limit flights to this country - 3 character country string
     * @param state Limit flights to this state
     * @param city Limit flights to this city
     * @param geometry Limit flights to those contained in the geometry
     * @param enhance Whether to return pilot and aircraft info in response
     * @param limit Maximum number of flights to return
     */
    // TODO: This method returns a paged response. Create a class to wrap+expose the paged response
    @GET(".")
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
        @Query("limit") limit: Int? = null,
    ): AirMapCall<List<Flight>>

    /**
     * Get a [Flight] identified by [id]. Set [enhance] to include detailed flight,
     * [Flight.pilot], and [Flight.aircraft] information in the response
     */
    @GET("{id}")
    @Wrapped(path = ["data"])
    fun getFlight(
        @Path("id") id: String,
        @Query("enhance") enhance: Boolean? = null,
    ): AirMapCall<Flight>

    /**
     * End the in-progress [Flight] identified by [id]
     */
    @POST("{id}/end")
    @Wrapped(path = ["data"])
    fun endFlight(
        @Path("id") id: String,
    ): AirMapCall<Flight>

    /**
     * Delete the [Flight] identified by [id]
     */
    @DELETE("{id}")
    @Wrapped(path = ["data"])
    fun deleteFlight(
        @Path("id") id: String,
    ): AirMapCall<Unit>

    /**
     * Start flight communications (telemetry) for [flightId]
     */
    @POST("{id}/start-comm")
    @Wrapped(path = ["data"])
    fun startComm(
        @Path("id") flightId: String,
    ): AirMapCall<Comm>

    /**
     * End flight communications (telemetry) for [flightId]
     */
    @POST("{id}/end-comm")
    @Wrapped(path = ["data"])
    fun endComm(
        @Path("id") flightId: String,
    ): AirMapCall<Unit>

    /**
     * Get the [FlightPlan] (if one is available) for [flightId]
     */
    @GET("{id}/plan")
    @Wrapped(path = ["data"])
    fun getFlightPlanByFlight(
        @Path("id") flightId: String,
    ): AirMapCall<FlightPlan>

    /**
     * Get a [FlightPlan] identified by [id]
     */
    @GET("plan/{id}")
    @Wrapped(path = ["data"])
    fun getFlightPlan(
        @Path("id") id: String,
    ): AirMapCall<FlightPlan>

    /**
     * Create a [flightPlan]
     */
    @POST("plan")
    @Wrapped(path = ["data"])
    // TODO: Create custom adapter with associated annotation to replace with string "null"
    fun createFlightPlan(
        @Body flightPlan: FlightPlan,
    ): AirMapCall<FlightPlan>

    /**
     * Update a [FlightPlan] identified by [id] to match [flightPlan]
     */
    @PATCH("plan/{id}")
    @Wrapped(path = ["data"])
    fun updateFlightPlan(
        @Path("id") id: String,
        @Body flightPlan: FlightPlan,
    ): AirMapCall<FlightPlan>

    /**
     * Submit [FlightPlan] identified by [id] as final
     */
    @POST("plan/{id}/submit")
    @FormUrlEncoded
    @Wrapped(path = ["data"])
    fun submitFlightPlan(
        @Path("id") id: String,
        @Field("public") isPublic: Boolean? = null,
    ): AirMapCall<FlightPlan>

    /**
     * Delete [FlightPlan] identified by [id]
     */
    @DELETE("plan/{id}")
    @Wrapped(path = ["data"])
    fun deleteFlightPlan(
        @Path("id") id: String,
    )

    /**
     * Get a [FlightBriefing] for a plan identified by [flightPlanId]
     */
    @GET("plan/{id}/briefing")
    @Wrapped(path = ["data"])
    fun getFlightPlanBriefing(
        @Path("id") flightPlanId: String,
    ): AirMapCall<FlightBriefing>

    /**
     * Get [FlightBriefing]s for all the [flightPlanIds]. The response [FlightBriefing] will only
     * have [FlightBriefing.flightPlanId] and [FlightBriefing.authorizations] populated
     */
    @GET("plan/batch/authorizations")
    @Wrapped(path = ["data"])
    fun getAuthorizations(
        @CommaSeparated @Query("flight_plan_ids") flightPlanIds: List<String>,
    ): AirMapCall<List<FlightBriefing>>
}
