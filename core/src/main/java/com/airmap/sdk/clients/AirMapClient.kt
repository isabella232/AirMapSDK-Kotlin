package com.airmap.sdk.clients

import com.airmap.sdk.models.AdvisoriesRequest
import com.airmap.sdk.models.Airspace
import com.airmap.sdk.models.EvaluationRequest
import com.airmap.sdk.models.FlightBriefing
import com.airmap.sdk.models.VerificationRequest
import com.aungkyawpaing.geoshi.model.Geometry
import java.util.Date

/**
 * This class is a delegate for the individual clients, which each represent a raw REST service. Any
 * methods defined in this file are for convenience only and serve as a wrapper around the raw REST
 * call made by the respective client
 */
@Suppress("MemberVisibilityCanBePrivate")
class AirMapClient(
    private val authClient: AuthClient,
    private val aircraftClient: AircraftClient,
    private val pilotClient: PilotClient,
    private val flightClient: FlightClient,
    private val airspaceClient: AirspaceClient,
    private val rulesClient: RulesClient,
    private val advisoryClient: AdvisoryClient,
    private val systemClient: SystemClient,
) : AuthClient by authClient,
    AircraftClient by aircraftClient,
    PilotClient by pilotClient,
    FlightClient by flightClient,
    AirspaceClient by airspaceClient,
    RulesClient by rulesClient,
    AdvisoryClient by advisoryClient,
    SystemClient by systemClient {
    // TODO: look into implications of moving this into the respective clients themselves
    //  Answer: Need to use @JvmDefault annotation and compile with Java 8
    //  This is specific Java 8 supported by all Android versions
    //  (https://developer.android.com/studio/write/java8-support)
    //  We should look into doing this (it will required some build parameter modifications)
    //  The benefit of this is that the helpers are closer to their actual definition

    // TODO: Some form of automatic translation when the real method is annotated with some
    //  specially defined annoation (e.g. @CommaSeparated). Track the following issue:
    //  https://github.com/square/retrofit/issues/626

    // We cannot use extension functions as this would be very ugly for Java syntax compatibility

    // AdvisoryClient
    /**
     * Get advisories for [geometry] and [rulesetIds] at a start
     * time of [start] and end time of [end]
     */
    fun getAdvisories(
        rulesetIds: List<String>,
        geometry: Geometry,
        start: Date? = null,
        end: Date? = null,
    ) = getAdvisories(
        AdvisoriesRequest(rulesetIds.joinToString(","), geometry, start, end)
    )

    // AirspaceClient
    /**
     * Get [Airspace]s specified by [ids]
     */
    fun getAirspaces(ids: List<String>) = getAirspaces(ids.joinToString(","))

    // PilotClient
    /**
     * Verify the logged in user's account with the [token] they received via SMS from a preceding
     * call to [sendVerificationToken]
     */
    fun verifySMS(token: Int) = verifySMS(VerificationRequest(token))

    // FlightClient
    /**
     * Get [FlightBriefing]s for all the [flightPlanIds]. The response [FlightBriefing] will only
     * have [FlightBriefing.flightPlanId] and [FlightBriefing.authorizations] populated
     */
    fun getAuthorizations(flightPlanIds: List<String>) =
        getAuthorizations(flightPlanIds.joinToString(","))

    /**
     * Convenience method to get currently ongoing flights that are publicly visible
     */
    fun getCurrentPublicFlights() = getFlights(startBefore = Date(), endAfter = Date())

    // RulesClient
    /**
     * Get rulesets identified by [rulesetIds]
     */
    fun getRulesets(rulesetIds: List<String>) = getRulesets(rulesetIds.joinToString(","))

    /**
     * Get an evaluated [com.airmap.sdk.models.FlightBriefing] for the given [geometry] and
     * [rulesetIds]
     */
    fun getEvaluation(geometry: Geometry, rulesetIds: List<String>) =
        getEvaluation(EvaluationRequest(geometry, rulesetIds.joinToString(",")))
}
