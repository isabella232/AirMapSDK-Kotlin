package com.airmap.sdk.clients

import com.airmap.sdk.models.AdvisoriesRequest
import com.airmap.sdk.models.Airspace
import com.airmap.sdk.models.Degrees
import com.airmap.sdk.models.Forecast
import com.airmap.sdk.networking.AirMapCall
import com.serjltt.moshi.adapters.Wrapped
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.Date

interface AdvisoryClient {
    /**
     * Get advisories for [AdvisoriesRequest.geometry] and [AdvisoriesRequest.rulesetIds] at a start
     * time of [AdvisoriesRequest.start] and end time of [AdvisoriesRequest.end]
     */
    @POST("airspace")
    @Wrapped(path = ["data"])
    fun getAdvisories(
        @Body advisoriesRequest: AdvisoriesRequest,
    ): AirMapCall<Airspace.Status>

    /**
     * Get advisories associated with [flightPlanId]
     */
    @GET("airspace/{flightPlanId}")
    @Wrapped(path = ["data"])
    fun getAdvisories(
        @Path("flightPlanId") flightPlanId: String
    ): AirMapCall<Airspace.Status>

    /**
     * Get weather data for the given [latitude], [longitude] by the hour, starting at [start] (if
     * given) and ending at [end] (if given)
     */
    @GET("weather")
    @Wrapped(path = ["data"])
    fun getWeather(
        @Query("latitude") latitude: Degrees,
        @Query("longitude") longitude: Degrees,
        @Query("start") start: Date?,
        @Query("end") end: Date?,
    ): AirMapCall<Forecast>
}
