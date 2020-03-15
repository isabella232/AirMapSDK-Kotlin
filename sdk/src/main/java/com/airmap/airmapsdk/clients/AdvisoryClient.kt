package com.airmap.airmapsdk.clients

import com.airmap.airmapsdk.Response
import com.airmap.airmapsdk.models.Airspace
import com.airmap.airmapsdk.models.Forecast
import com.aungkyawpaing.geoshi.model.Geometry
import com.serjltt.moshi.adapters.Wrapped
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.Date

interface AdvisoryClient {
    @GET("airspace")
    @Wrapped(path = ["data"])
    // TODO
//    @BodyJson // Implicitly convert parameters (RequestBodyConverter??) as fields in an object and apply the @Body annotation to those so that Retrofit uses the JSON serializers
    fun getAdvisories(
        @Query("rulesets") rulesets: List<String>,
        @Query("geometry") geometry: Geometry,
        @Query("start") start: Date? = null,
        @Query("end") end: Date? = null
// TODO        @Field("flight_features") flightFeatures: List<FlightFeatureValue>? = null
    ): Response<Airspace.Status>

    // TODO: Retrofit doesn't support parsing Date/objects directly when it's a Query param :(
    //  Look into how we can possibly get around this
    @GET("weather")
    @Wrapped(path = ["data"])
    fun getWeather(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("start") start: String,
        @Query("end") end: String
    ): Response<Forecast>
}
