package com.airmap.sdk.clients

import com.airmap.sdk.models.Comm
import com.airmap.sdk.models.Flight
import com.airmap.sdk.models.FlightBriefing
import com.airmap.sdk.models.FlightPlan
import com.airmap.sdk.networking.AirMapCall
import com.aungkyawpaing.geoshi.model.Geometry
import com.serjltt.moshi.adapters.Wrapped
import retrofit2.http.Body
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

    @GET("{id}")
    @Wrapped(path = ["data"])
    fun getFlight(
        @Path("id") flightId: String,
        @Query("enhance") enhance: Boolean = false,
    ): AirMapCall<Flight>

    @POST("{id}/end")
    @Wrapped(path = ["data"])
    fun endFlight(
        @Path("id") flightId: String,
    ): AirMapCall<Flight>

    @POST("{id}/delete")
    @Wrapped(path = ["data"])
    fun deleteFlight(
        @Path("id") flightId: String,
    ): AirMapCall<Unit>

    @POST("{id}/start-comm")
    @Wrapped(path = ["data"])
    fun startComm(
        @Path("id") flightId: String,
    ): AirMapCall<Comm>

    @POST("{id}/end-comm")
    @Wrapped(path = ["data"])
    fun endComm(
        @Path("id") flightId: String,
    ): AirMapCall<Unit>

    @GET("{id}/plan")
    @Wrapped(path = ["data"])
    fun getFlightPlanByFlight(
        @Path("id") flightId: String,
    ): AirMapCall<FlightPlan>

    @GET("plan/{id}")
    @Wrapped(path = ["data"])
    fun getFlightPlan(
        @Path("id") id: String,
    ): AirMapCall<FlightPlan>

    @POST("plan")
    @Wrapped(path = ["data"])
    // TODO: Create custom adapter with associated annotation to replace with string "null"
    fun createFlightPlan(
        @Body flightPlan: FlightPlan,
    ): AirMapCall<FlightPlan>

    @PATCH("plan/{id}")
    @Wrapped(path = ["data"])
    fun updateFlightPlan(
        @Path("id") id: String,
        @Body flightPlan: FlightPlan,
    ): AirMapCall<FlightPlan>

    @POST("plan/{id}/submit")
    @FormUrlEncoded
    @Wrapped(path = ["data"])
    fun submitFlightPlan(
        @Path("id") flightPlanId: String,
        @Field("public") isPublic: Boolean? = null,
    ): AirMapCall<FlightPlan>

    @GET("plan/{id}/briefing")
    @Wrapped(path = ["data"])
    fun getFlightPlanBriefing(
        @Path("id") flightPlanId: String,
    ): AirMapCall<FlightBriefing>

    // The response FlightBriefing object will only have flightPlanId and authorizations populated
    @GET("plan/batch/authorizations")
    @Wrapped(path = ["data"])
    fun getAuthorizations(
        @Query("flight_plan_ids") flightPlanIds: String,
    ): AirMapCall<List<FlightBriefing>>
}