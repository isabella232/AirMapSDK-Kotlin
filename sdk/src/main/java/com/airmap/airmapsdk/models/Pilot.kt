package com.airmap.airmapsdk.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import org.threeten.bp.LocalDateTime

@JsonClass(generateAdapter = true)
data class VerificationStatus(val phone: String, val email: String)

@JsonClass(generateAdapter = true)
data class FlightStatistics(val total: Int, @Json(name = "last_flight_time") val lastFlightTime: LocalDateTime)

@JsonClass(generateAdapter = true)
data class AircraftStatistics(val total: Int)

@JsonClass(generateAdapter = true)
data class Statistics(val flight: FlightStatistics, val aircraft: AircraftStatistics)

@JsonClass(generateAdapter = true)
data class Pilot(
    val id: String,
    val email: String?,
    @Json(name = "first_name") val firstName: String?,
    @Json(name = "last_name") val lastName: String?,
    val username: String?,
    @Json(name = "picture_url") val pictureUrl: String?,
    val phone: String?,
    @Json(name = "anonymized_id") val anonymizedId: String?,
    val verificationStatus: VerificationStatus,
    val statistics: Statistics,
    @Json(name = "user_metadata") val userMetadata: Map<String, Any?>?,
    @Json(name = "app_metadata") val appMetadata: Map<String, Any?>?
)