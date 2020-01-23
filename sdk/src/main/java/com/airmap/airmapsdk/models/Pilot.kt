package com.airmap.airmapsdk.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import org.threeten.bp.LocalDateTime

@JsonClass(generateAdapter = true)
data class VerificationStatus(
    @Json(name = "phone") val phone: String,
    @Json(name = "email") val email: String
)

@JsonClass(generateAdapter = true)
data class FlightStatistics(
    @Json(name = "total") val total: Int,
    @Json(name = "last_flight_time") val lastFlightTime: LocalDateTime
)

@JsonClass(generateAdapter = true)
data class AircraftStatistics(
    @Json(name = "total") val total: Int
)

@JsonClass(generateAdapter = true)
data class Statistics(
    @Json(name = "flight") val flight: FlightStatistics,
    @Json(name = "aircraft") val aircraft: AircraftStatistics
)

@JsonClass(generateAdapter = true)
data class Pilot(
    @Json(name = "id") val id: String,
    @Json(name = "email") val email: String?,
    @Json(name = "first_name") val firstName: String?,
    @Json(name = "last_name") val lastName: String?,
    @Json(name = "username") val username: String?,
    @Json(name = "picture_url") val pictureUrl: String?,
    @Json(name = "phone") val phone: String?,
    @Json(name = "anonymized_id") val anonymizedId: String?,
    @Json(name = "verification_status") val verificationStatus: VerificationStatus,
    @Json(name = "statistics") val statistics: Statistics,
    @Json(name = "user_metadata") val userMetadata: Map<String, Any?>?,
    @Json(name = "app_metadata") val appMetadata: Map<String, Any?>?
)

@JsonClass(generateAdapter = true)
data class VerificationRequest(
    @Json(name = "token") val token: String
)

@JsonClass(generateAdapter = true)
data class VerificationResult(
    @Json(name = "verified") val verified: Boolean
)
