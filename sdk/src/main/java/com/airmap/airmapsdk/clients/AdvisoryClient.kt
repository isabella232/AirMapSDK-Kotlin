package com.airmap.airmapsdk.clients

import com.airmap.airmapsdk.Response
import com.airmap.airmapsdk.models.Airspace
import com.aungkyawpaing.geoshi.model.Geometry
import com.serjltt.moshi.adapters.Wrapped
import java.util.Date
import retrofit2.http.GET
import retrofit2.http.Query

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
}
