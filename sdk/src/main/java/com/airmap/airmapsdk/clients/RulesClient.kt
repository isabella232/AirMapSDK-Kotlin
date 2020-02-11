package com.airmap.airmapsdk.clients

import com.airmap.airmapsdk.Response
import com.airmap.airmapsdk.models.Airspace
import com.airmap.airmapsdk.models.FlightBriefing
import com.airmap.airmapsdk.models.Jurisdiction
import com.airmap.airmapsdk.models.Ruleset
import com.aungkyawpaing.geoshi.model.Geometry
import com.serjltt.moshi.adapters.Wrapped
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import java.util.*

interface RulesClient {
    @GET("{id}")
    @Wrapped(path = ["data"])
    fun getRuleset(id: String): Response<Ruleset>

    @GET("")
    @Wrapped(path = ["data"])
    @FormUrlEncoded
    fun getRulesets(@Field("rulesets") rulesets: List<String>): Response<List<Ruleset>>

    @GET("")
    @Wrapped(path = ["data"])
    @FormUrlEncoded
    fun getRulesets(@Field("geometry") geometry: Geometry): Response<List<Ruleset>>

    @GET("")
    @Wrapped(path = ["data"])
    @FormUrlEncoded
    fun getRulesets(@Field("latitude") latitude: Double, @Field("longitude") longitude: Double): Response<List<Ruleset>>

    @GET("")
    @Wrapped(path = ["data"])
    fun getEvaluation(rulesets: List<String>, geometry: Geometry): Response<FlightBriefing>

    @GET("")
    @Wrapped(path = ["data"])
    // TODO: This is a bit weird, all the parameters are appended together
    fun getJurisdictions(southwestLatitude: Double, southwestLongitude: Double, northeastLatitude: Double, northeastLongitude: Double)

    @GET("")
    @Wrapped(path = ["data"])
    @FormUrlEncoded
    fun getJurisdictions(@Field("geometry") geometry: Geometry): Response<List<Jurisdiction>>
}
