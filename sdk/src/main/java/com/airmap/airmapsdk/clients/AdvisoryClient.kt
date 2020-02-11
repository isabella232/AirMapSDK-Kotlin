package com.airmap.airmapsdk.clients

import com.airmap.airmapsdk.Response
import com.airmap.airmapsdk.models.Airspace
import com.airmap.airmapsdk.models.FlightBriefing
import com.aungkyawpaing.geoshi.model.Geometry
import com.serjltt.moshi.adapters.Wrapped
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import java.util.*

interface AdvisoryClient {
    @GET("airspace")
    @Wrapped(path = ["data"])
    @FormUrlEncoded
    fun getAdvisories(@Field("rulesets") rulesets: List<String>, @Field("geometry") geometry: Geometry, @Field("start") start: Date? = null, @Field("end") end: Date? = null, @Field("flight_features") flightFeatures: List<FlightFeatureValue>): Response<Airspace.Status>
}
