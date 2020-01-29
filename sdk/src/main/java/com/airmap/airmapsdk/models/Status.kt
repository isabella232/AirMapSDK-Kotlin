package com.airmap.airmapsdk.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.*

@JsonClass(generateAdapter = true)
data class Airspace(
    @Json(name = "id") val id: String,
    @Json(name = "name") val name: String,
    @Json(name = "type") val type: Type,
    @Json(name = "country") val country: String,
    @Json(name = "state") val state: String,
    @Json(name = "city") val city: String,
    @Json(name = "geometry") val geometry: Geometry,
    @Json(name = "related_geometry.property_boundary.geometry") val propertyBoundary: Geometry // TODO: this is found at related_geometry.property_boundary.geometry
) {
    // TODO: @JsonClass(generateAdapter = true)
    enum class Type(private val apiName: String) {
        Airport("airport"),
        AMAField("ama_field"),
        City("city"),
        ControlledAirspace("controlled_airspace"),
        Country("country"),
        County("county"),
        Custom("custom"),
        Embassy("embassy"),
        Emergency("emergency"),
        FederalBuilding("federal_building"),
        FIR("fir"),
        Fire("fire"),
        Gliderport("gliderport"),
        HazardArea("hazard_area"),
        Heliport("heliport"),
        Highway("highway"),
        Hospital("hospital"),
        IndustrialProperty("industrial_property"),
        JapanBase("jpn_base"),
        MilitaryProperty("military_property"),
        NOTAM("notam"),
        Park("park"),
        PoliceStation("police_station"),
        Powerline("powerline"),
        PowerPlant("power_plant"),
        Prison("prison"),
        Railway("railway"),
        RecreationalArea("recreational_area"),
        ResidentialProperty("residential_property"),
        School("school"),
        SeaplaneBase("seaplane_base"),
        SpecialUse("special_use_airspace"),
        Stadium("stadium"),
        State("state"),
        Subprefecture("subprefecture"),
        Supercity("supercity"),
        TFR("tfr"),
        UltralightField("ulm_field"),
        University("university"),
        Waterway("waterway"),
        Wildfire("wildfire");

        override fun toString() = apiName
    }
}

@JsonClass(generateAdapter = true)
data class Status(
    @Json(name = "color") val color: Color,
    @Json(name = "advisories") val advisories: List<Advisory>
)

// TODO: Should we make this an inner class of Status?
@JsonClass(generateAdapter = true)
enum class Color(private val apiName: String) {
    Red("red"),
    Orange("orange"),
    Yellow("yellow"),
    Green("green"),
}

@JsonClass(generateAdapter = true)
data class Advisory(
    @Json(name = "id") val id: String,
    @Json(name = "name") val name: String,
    @Json(name = "organization_id") val organizationId: String,
    @Json(name = "type") val type: Airspace.Type,
    @Json(name = "country") val country: String,
    @Json(name = "state") val state: String,
    @Json(name = "city") val city: String,
    @Json(name = "last_updated") val lastUpdated: Date,
    @Json(name = "color") val color: Color,
    @Json(name = "distance") val distance: Int,
    @Json(name = "latitude") val latitude: Double,
    @Json(name = "longitude") val longitude: Double,
    @Json(name = "geometry") val geometry: Geometry,
    @Json(name = "requirements") val requirements: Requirements,
    @Json(name = "properties") val properties: AirspaceProperties
)

@JsonClass(generateAdapter = true)
data class Requirements(
    @Json(name = "notice") val notice: Notice
)

@JsonClass(generateAdapter = true)
data class Notice(
    @Json(name = "digital") val digital: Boolean,
    @Json(name = "phone") val phone: String
)

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
    @Json(name = "runways") val runways: List<Runway>?,
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

// TODO: Verify, Swift SDK has different fields...
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

// TODO: Rest of the properties
