package com.airmap.airmapsdk.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.*

@JsonClass(generateAdapter = true)
data class Flight(
    val flightId: String,
    @Json(name = "flight_plan_id") val flightPlanId: String? = null,
    @Json(name = "pilot_id") val pilotId: String,
    val pilot: Pilot,
    val latitude: Double,
    val longitude: Double, // TODO: Provide a MapBox LatLng getter
    @Json(name = "max_altitude") val maxAltitude: Double,
    val aircraft: Aircraft? = null,
    @Json(name = "aircraft_id") val aircraftId: String? = null,
    @Json(name = "created_at") val createdAt: Date = Date(),
    @Json(name = "start_time") val startTime: Date, // TODO: Need to use "now" for start_time value if start_time is in the past/close to now during flight creation
    @Json(name = "end_time") val endTime: Date,
    val city: String,
    val state: String,
    val country: String,
    val buffer: Double,
    val notify: Boolean,
    @Json(name = "public") val isPublic: Boolean,
    val permits: List<String> = emptyList(),
    val geometry: Geometry?
) {
//    val f = Flight().apply { country = "" }
}

