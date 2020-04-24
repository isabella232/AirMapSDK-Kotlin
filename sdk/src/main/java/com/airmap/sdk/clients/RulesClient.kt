package com.airmap.sdk.clients

import com.airmap.sdk.models.EvaluationRequest
import com.airmap.sdk.models.FlightBriefing
import com.airmap.sdk.models.Ruleset
import com.airmap.sdk.networking.AirMapCall
import com.airmap.sdk.networking.CommaSeparated
import com.aungkyawpaing.geoshi.model.Geometry
import com.serjltt.moshi.adapters.Wrapped
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
        @CommaSeparated @Query("rulesets") rulesetIds: List<String>,
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
