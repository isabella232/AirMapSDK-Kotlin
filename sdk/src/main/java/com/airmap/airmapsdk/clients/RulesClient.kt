package com.airmap.airmapsdk.clients

import com.airmap.airmapsdk.Response
import com.airmap.airmapsdk.models.FlightBriefing
import com.airmap.airmapsdk.models.Jurisdiction
import com.airmap.airmapsdk.models.Ruleset
import com.aungkyawpaing.geoshi.model.Geometry
import com.serjltt.moshi.adapters.Wrapped
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface RulesClient {
    @GET("{id}")
    @Wrapped(path = ["data"])
    fun getRuleset(id: String): Response<Ruleset>

    @GET(".")
    @FormUrlEncoded
    @Wrapped(path = ["data"])
    fun getRulesets(@Field("rulesets") rulesets: List<String>): Response<List<Ruleset>>

    @POST(".")
    @Wrapped(path = ["data"])
    fun getRulesets(@Body @Wrapped(path = ["geometry"]) geometry: Geometry): Response<List<Ruleset>>

    @GET(".")
    @FormUrlEncoded
    @Wrapped(path = ["data"])
    fun getRulesets(
        @Field("latitude") latitude: Double,
        @Field("longitude") longitude: Double
    ): Response<List<Ruleset>>

    @POST("evaluation")
    @Wrapped(path = ["data"])
    fun getEvaluation(
        @Body evaluationRequest: EvaluationRequest
        // @Query("geometry") geometry: Geometry,
        // @Query("rulesets") rulesets: List<String>
    ): Response<FlightBriefing>

    // TODO: All the Jurisdiction calls require extra processing
    @GET(".")
    @Wrapped(path = ["data"])
    // TODO: This is a bit weird, all the parameters are appended together
    fun getJurisdictions(
        southwestLatitude: Double,
        southwestLongitude: Double,
        northeastLatitude: Double,
        northeastLongitude: Double
    )

    @GET(".")
    @FormUrlEncoded
    @Wrapped(path = ["data"])
    fun getJurisdictions(@Field("geometry") geometry: Geometry): Response<List<Jurisdiction>>
}

@JsonClass(generateAdapter = true)
data class EvaluationRequest(
    @Json(name = "geometry") val geometry: Geometry,
    @Json(name = "rulesets") val rulesets: String
)
