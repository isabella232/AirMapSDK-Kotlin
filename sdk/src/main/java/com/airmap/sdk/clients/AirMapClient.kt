package com.airmap.sdk.clients

import com.airmap.sdk.models.AdvisoriesRequest
import com.airmap.sdk.models.EvaluationRequest
import com.airmap.sdk.models.UpdatePilotRequest
import com.airmap.sdk.models.VerificationRequest
import com.aungkyawpaing.geoshi.model.Geometry
import java.util.Date

// The clients represent the raw REST API. Any extra methods defined in AirMapClient are convenience
class AirMapClient(
    private val aircraftClient: AircraftClient,
    private val pilotClient: PilotClient,
    private val flightClient: FlightClient,
    private val airspaceClient: AirspaceClient,
    private val rulesClient: RulesClient,
    private val advisoryClient: AdvisoryClient,
) : AircraftClient by aircraftClient,
    PilotClient by pilotClient,
    FlightClient by flightClient,
    AirspaceClient by airspaceClient,
    RulesClient by rulesClient,
    AdvisoryClient by advisoryClient {
    // TODO: look into implications of moving this into the respective clients themselves
    //  Answer: Need to use @JvmDefault annotation and compile with Java 8
    //  This is specific Java 8 supported by all Android versions
    //  (https://developer.android.com/studio/write/java8-support)
    //  We should look into doing this (it will required some build parameter modifications)
    //  The benefit of this is that the helpers are closer to their actual definition

    // TODO: Some form of automatic translation when the real method is annotated with some
    //  specially defined annoation (e.g. @CommaSeparated). Track the following issue:
    //  https://github.com/square/retrofit/issues/626

    // AdvisoryClient
    fun getAdvisories(
        rulesetIds: List<String>,
        geometry: Geometry,
        start: Date? = null,
        end: Date? = null,
    ) = getAdvisories(
        AdvisoriesRequest(
            rulesetIds.joinToString(","), geometry, start, end
        )
    )

    // PilotClient
    fun verifySMS(token: Int) = verifySMS(VerificationRequest(token))
    fun updatePilot(
        firstName: String? = null,
        lastName: String? = null,
        username: String? = null,
        email: String? = null,
        phone: String? = null,
        appMetadata: Map<String, Any>? = null,
        userMetadata: Map<String, Any>? = null,
    ) = updatePilot(
        UpdatePilotRequest(
            firstName, lastName, username, email, phone, appMetadata, userMetadata
        )
    )

    // FlightClient
    // TODO: fun getPublicFlights() (make use of getFlights with custom parameters)

    // RulesClient
    fun getEvaluation(geometry: Geometry, rulesetIds: List<String>) = getEvaluation(
        EvaluationRequest(geometry, rulesetIds.joinToString(","))
    )
}
