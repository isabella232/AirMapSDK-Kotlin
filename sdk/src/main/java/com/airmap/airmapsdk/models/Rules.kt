package com.airmap.airmapsdk.models

import com.airmap.airmapsdk.clients.FlightFeatureValue
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Ruleset(
    @Json(name = "id") val id: String,
    @Json(name = "name") val name: String?,
    @Json(name = "short_name") val shortName: String?,
    @Json(name = "jurisdiction") val jurisdiction: Jurisdiction?,
    @Json(name = "type") val type: Type,
    @Json(name = "default") val isDefault: Boolean = false,
    @Json(name = "summary") val summary: String,
    @Json(name = "layers") val layers: List<String>,
    @Json(name = "rules") val rules: List<Rule>
) {
    enum class Type(private val apiName: String) {
        PickOne("pickone"),
        Pick1("pick1"),
        Required("required"),
        Optional("optional"),
    }
}

@JsonClass(generateAdapter = true)
data class Rule(
    @Json(name = "short_text") val shortText: String?,
    @Json(name = "description") val description: String?,
    @Json(name = "status") val status: Status?,
    @Json(name = "display_order") val displayOrder: Int?,
    @Json(name = "flight_features") val flightFeatures: List<FlightFeatureValue>
) {
    enum class Status(private val apiName: String) {
        Conflicting("conflicting"),
        NotConflicting("not_conflicting"),
        MissingInfo("missing_info"),
        Informational("informational"),
        InformationRules("information_rules"),
    }
}

@JsonClass(generateAdapter = true)
data class Authority(
    @Json(name = "id") val id: String,
    @Json(name = "name") val name: String
)

@JsonClass(generateAdapter = true)
data class Authorization(
    @Json(name = "status") val status: Status,
    @Json(name = "authority") val authority: Authority?,
    @Json(name = "description") val description: String?,
    @Json(name = "message") val message: String?,
    @Json(name = "reference_number") val referenceNumber: String?
) {
    // TODO: Verify how to serialize/deserialize
    @JsonClass(generateAdapter = true)
    enum class Status(private val apiName: String) {
        NotRequested("not_requested"),
        RejectedUponSubmission("rejected_upon_submission"),
        AuthorizedUponSubmission("authorized_upon_submission"),
        ManualAuthorization("manual_authorization"),
        Pending("pending"),
        Accepted("accepted"),
        Rejected("rejected"),
        Cancelled("cancelled")
    }
}

@JsonClass(generateAdapter = true)
data class Jurisdiction(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String,
    @Json(name = "region") val region: Region,
    @Json(name = "rulesets") val rulesets: List<Ruleset>
)

// TODO: Verify how to serialize/deserialize
@JsonClass(generateAdapter = true)
enum class Region(private val apiName: String) {
    National("national"),
    State("statee"),
    County("county"),
    City("city"),
    Local("local")
}

@JsonClass(generateAdapter = true)
data class Validation(
    @Json(name = "data") val data: String?,
    @Json(name = "status") val status: Status?,
    @Json(name = "message") val message: String?,
    @Json(name = "feature") val feature: Feature?,
    @Json(name = "authority") val authority: Authority?
) {
    @JsonClass(generateAdapter = true)
    enum class Status(private val apiName: String) {
        Valid("valid"),
        Invalid("invalid"),
        Unknown("unknown")
    }
}

@JsonClass(generateAdapter = true)
data class Feature(
    @Json(name = "code") val code: String,
    @Json(name = "description") val description: String
)
