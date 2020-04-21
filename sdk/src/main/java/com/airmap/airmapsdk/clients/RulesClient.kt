package com.airmap.airmapsdk.clients

import com.airmap.airmapsdk.models.FlightBriefing
import com.airmap.airmapsdk.models.Ruleset
import com.airmap.airmapsdk.networking.AirMapCall
import com.aungkyawpaing.geoshi.model.Geometry
import com.serjltt.moshi.adapters.Wrapped
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface RulesClient {
    @GET("{id}")
    @Wrapped(path = ["data"])
    fun getRuleset(
        @Path("id") id: String,
    ): AirMapCall<Ruleset>

    @GET("rule")
    @Wrapped(path = ["data"])
    fun getRulesets(
        @Query("rulesets") rulesetIds: String,
    ): AirMapCall<List<Ruleset>>

    @POST(".")
    @Wrapped(path = ["data"])
    fun getRulesets(
        @Body @Wrapped(path = ["geometry"]) geometry: Geometry,
    ): AirMapCall<List<Ruleset>>

    @POST("evaluation")
    @Wrapped(path = ["data"])
    fun getEvaluation(
        @Body evaluationRequest: EvaluationRequest,
    ): AirMapCall<FlightBriefing>
}

@JsonClass(generateAdapter = true)
data class EvaluationRequest(
    @Json(name = "geometry") val geometry: Geometry,
    @Json(name = "rulesets") val rulesets: String,
)
