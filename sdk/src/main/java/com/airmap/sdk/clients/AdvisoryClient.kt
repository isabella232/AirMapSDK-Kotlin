package com.airmap.sdk.clients

import com.airmap.sdk.models.AdvisoriesRequest
import com.airmap.sdk.models.Airspace
import com.airmap.sdk.models.Forecast
import com.airmap.sdk.networking.AirMapCall
import com.serjltt.moshi.adapters.Wrapped
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AdvisoryClient {
    @POST("airspace")
    @Wrapped(path = ["data"])
    fun getAdvisories(
        @Body advisoriesRequest: AdvisoriesRequest,
    ): AirMapCall<Airspace.Status>

    // TODO: Retrofit doesn't support parsing Date/objects directly when it's a Query param :(
    //  Look into how we can possibly get around this
    @GET("weather")
    @Wrapped(path = ["data"])
    fun getWeather(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("start") start: String,
        @Query("end") end: String,
    ): AirMapCall<Forecast>
}
