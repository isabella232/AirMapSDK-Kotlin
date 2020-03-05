package com.airmap.airmapsdk.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.*

// TODO: Verify all these fields, Swift SDK has different fields...
// TODO: Create custom sealed class deserializer
sealed class AirspaceProperties

@JsonClass(generateAdapter = true)
data class AirportProperties(
    @Json(name = "url") val url: String?,
    @Json(name = "description") val description: String?,
    @Json(name = "icao") val icao: String?,
    @Json(name = "phone") val phone: String?,
    @Json(name = "ownership") val ownership: String?,
    @Json(name = "ifr") val ifr: Boolean?,
    @Json(name = "iata") val iata: String?,
    @Json(name = "paved") val paved: Boolean?,
    @Json(name = "tower") val tower: Boolean?,
    @Json(name = "runways") val runways: List<Runway> = mutableListOf(),
    @Json(name = "elevation") val elevation: Int?,
    @Json(name = "icao_country") val icaoCountry: String?,
    @Json(name = "longest_runway") val longestRunway: Int?
) : AirspaceProperties() {
    @JsonClass(generateAdapter = true)
    data class Runway(
        @Json(name = "name") val name: String,
        @Json(name = "length") val length: Int,
        @Json(name = "bearing") val bearing: Int
    )
}

@JsonClass(generateAdapter = true)
data class ControlledAirspaceProperties(
    @Json(name = "url") val url: String?,
    @Json(name = "description") val description: String?,
    @Json(name = "class_airspace") val classAirspace: String?,
    @Json(name = "airport_identifier") val airportIdentifier: String?,
    @Json(name = "laanc") val laanc: Boolean = false,
    @Json(name = "authorization") val authorization: Boolean = false

): AirspaceProperties()

@JsonClass(generateAdapter = true)
data class EmergencyProperties(
    @Json(name = "url") val url: String?,
    @Json(name = "description") val description: String?,
    @Json(name = "agency_id") val agencyId: String?,
    @Json(name = "date_effective") val dateEffective: Date?
): AirspaceProperties()

@JsonClass(generateAdapter = true)
data class HeliportProperties(
    @Json(name = "url") val url: String?,
    @Json(name = "description") val description: String?,
    @Json(name = "phone") val phone: String?,
    @Json(name = "use") val use: String?
): AirspaceProperties()

@JsonClass(generateAdapter = true)
data class NotamProperties(
    @Json(name = "url") val url: String?,
    @Json(name = "description") val description: String?,
    @Json(name = "effective_start") val effectiveStart: Date?,
    @Json(name = "effective_end") val effectiveEnd: Date?
): AirspaceProperties()

@JsonClass(generateAdapter = true)
data class ParkProperties(
    @Json(name = "url") val url: String?,
    @Json(name = "description") val description: String?,
    @Json(name = "size") val size: Int?,
    @Json(name = "type") val type: String?
): AirspaceProperties()

@JsonClass(generateAdapter = true)
data class PowerPlantProperties(
    @Json(name = "url") val url: String?,
    @Json(name = "description") val description: String?,
    @Json(name = "tech") val tech: String?,
    @Json(name = "plant_code") val plantCode: Int?
): AirspaceProperties()

@JsonClass(generateAdapter = true)
data class SchoolProperties(
    @Json(name = "url") val url: String?,
    @Json(name = "description") val description: String?,
    @Json(name = "type") val type: String?,
    @Json(name = "building") val building: Boolean?,
    @Json(name = "way_area") val wayArea: Double?
): AirspaceProperties()

@JsonClass(generateAdapter = true)
data class SpecialUseProperties(
    @Json(name = "url") val url: String?,
    @Json(name = "description") val description: String?,
    @Json(name = "currently_active") val currentlyActive: Boolean?
): AirspaceProperties()

@JsonClass(generateAdapter = true)
data class TFRProperties(
    @Json(name = "url") val url: String?,
    @Json(name = "description") val description: String?,
    @Json(name = "effective_start") val effectiveStart: Date?,
    @Json(name = "effective_end") val effectiveEnd: Date?
): AirspaceProperties()

@JsonClass(generateAdapter = true)
data class WildfireProperties(
    @Json(name = "url") val url: String?,
    @Json(name = "description") val description: String?,
    @Json(name = "size") val size: Int?,
    @Json(name = "date_effective") val dateEffective: Date?
): AirspaceProperties()
