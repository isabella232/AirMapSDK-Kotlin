package com.airmap.airmapsdk.models

import com.aungkyawpaing.geoshi.model.Geometry
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.Date

@JsonClass(generateAdapter = true)
data class Flight(
    @Json(name = "id") val id: String,
    @Json(name = "flight_plan_id") val flightPlanId: String? = null,
    @Json(name = "pilot_id") val pilotId: String,
    @Json(name = "pilot") val pilot: Pilot?,
    @Json(name = "latitude") val latitude: Double,
    @Json(name = "longitude") val longitude: Double, // TODO: Provide a MapBox LatLng getter
    @Json(name = "max_altitude") val maxAltitude: Double,
    @Json(name = "aircraft") val aircraft: Aircraft? = null,
    @Json(name = "aircraft_id") val aircraftId: String? = null,
    @Json(name = "created_at") val createdAt: Date,
    @Json(name = "start_time") val startTime: Date, // TODO: Need to use "now" for start_time value if start_time is in the past/close to now during flight creation
    @Json(name = "end_time") val endTime: Date,
    @Json(name = "city") val city: String?,
    @Json(name = "state") val state: String?,
    @Json(name = "country") val country: String?,
    @Json(name = "buffer") val buffer: Double,
    @Json(name = "notify") val notify: Boolean,
    @Json(name = "public") val isPublic: Boolean,
    @Json(name = "permits") val permits: List<String> = mutableListOf(),
    @Json(name = "statuses") val statuses: List<Status> = mutableListOf(),
    @Json(name = "geometry") val geometry: Geometry?
) {
    @JsonClass(generateAdapter = true)
    data class Status(
        @Json(name = "id") val id: String,
        @Json(name = "manager_id") val managerId: String,
        @Json(name = "status") val status: Type
    ) {
        enum class Type {
            @Json(name = "accepted") Accepted,
            @Json(name = "rejected") Rejected,
            @Json(name = "pending") Pending,
        }
    }
}

@JsonClass(generateAdapter = true)
data class FlightPlan(
    val id: String,
    @Json(name = "flight_id") val flightId: String,
    @Json(name = "pilot_id") val pilotId: String,
    @Json(name = "aircraft_id") val aircraftId: String,
    @Json(name = "start_time") val startTime: Date,
    @Json(name = "end_time") val endTime: Date,
    @Json(name = "created_at") val createdAt: Date?,
    @Json(name = "creation_date") val creationDate: Date?,
    @Json(name = "buffer") val buffer: Double,
    @Json(name = "geometry") val geometry: Geometry,
    @Json(name = "max_altitude") val maxAltitude: Double?,
    @Json(name = "takeoff_latitude") val takeoffLatitude: Double,
    @Json(name = "takeoff_longitude") val takeoffLongitude: Double,
    @Json(name = "min_altitude_agl") val minAltitudeAgl: Double?,
    @Json(name = "max_altitude_agl") val maxAltitudeAgl: Double?,
    @Json(name = "rulests") val rulesetIds: List<String> = mutableListOf(),
//    @Json(name = "flight_features") val flightFeatures: Map<String, Any> = mutableMapOf(), // TODO: Check how Flight Features are returned
    @Json(name = "public") val isPublic: Boolean = false,
    @Json(name = "notify") val notify: Boolean = false
)

@JsonClass(generateAdapter = true)
data class FlightBriefing(
    @Json(name = "flight_plan_id") val flightPlanId: String,
    @Json(name = "color") val color: String?,
    @Json(name = "created_at") val createdAt: Date?,
    @Json(name = "rulesets") val rulesets: List<Ruleset> = mutableListOf(),
    @Json(name = "airspace") val airspace: Airspace.Status?,
    @Json(name = "validations") val validations: List<Validation> = mutableListOf(), // TODO: Remove? (see: https://github.com/airmap/AirMapSDK-Swift/commit/ddaa437cf0623367c21536ae030efc2d42bed903)
    @Json(name = "authorizations") val authorizations: List<Authorization> = mutableListOf()
)
