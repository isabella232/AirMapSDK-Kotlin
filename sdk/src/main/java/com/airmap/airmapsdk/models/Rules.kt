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
    @Json(name = "layers") val layers: List<String> = mutableListOf(),
    @Json(name = "rules") val rules: List<Rule> = mutableListOf()
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
    @Json(name = "flight_features") val flightFeatures: List<FlightFeatureValue> = mutableListOf()
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
    enum class Status {
        @Json(name = "not_requested") NotRequested,
        @Json(name = "rejected_upon_submission") RejectedUponSubmission,
        @Json(name = "authorized_upon_submission") AuthorizedUponSubmission,
        @Json(name = "manual_authorization") ManualAuthorization,
        @Json(name = "pending") Pending,
        @Json(name = "accepted") Accepted,
        @Json(name = "rejected") Rejected,
        @Json(name = "cancelled") Cancelled,
    }
}

@JsonClass(generateAdapter = true)
data class Jurisdiction(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String,
    @Json(name = "region") val region: Region,
    @Json(name = "rulesets") val rulesets: List<Ruleset> = mutableListOf()
)

// TODO: Verify how to serialize/deserialize
enum class Region {
    @Json(name = "national") National,
    @Json(name = "statee") State,
    @Json(name = "county") County,
    @Json(name = "city") City,
    @Json(name = "local") Local,
}

@JsonClass(generateAdapter = true)
data class Validation(
    @Json(name = "data") val data: String?,
    @Json(name = "status") val status: Status?,
    @Json(name = "message") val message: String?,
    @Json(name = "feature") val feature: Feature?,
    @Json(name = "authority") val authority: Authority?
) {
    enum class Status {
        @Json(name = "valid") Valid,
        @Json(name = "invalid") Invalid,
        @Json(name = "unknown") Unknown,
    }
}

@JsonClass(generateAdapter = true)
data class Feature(
    @Json(name = "code") val code: String,
    @Json(name = "description") val description: String
)
