package com.airmap.sdk.models

import com.aungkyawpaing.geoshi.model.Geometry
import com.serjltt.moshi.adapters.FallbackEnum
import com.serjltt.moshi.adapters.Wrapped
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.Date

@JsonClass(generateAdapter = true)
data class Airspace(
    @Json(name = "id") val id: String,
    @Json(name = "airspace_uuid") val uuid: String?,
    @Json(name = "name") val name: String,
    @Json(name = "type") val type: Type,
    @Json(name = "country") val country: String,
    @Json(name = "state") val state: String?,
    @Json(name = "city") val city: String?,
    @Json(name = "geometry") val geometry: Geometry,
    // TODO: dot notation!
    @Wrapped(path = ["related_geometry", "property_boundary", "geometry"])
    val propertyBoundary: Geometry?,
) {
    // Not every Airspace Type has properties
    @FallbackEnum(name = "Unknown")
    enum class Type {
        @Json(name = "airport") Airport,

        // Academy of Model Aeronautics (AMA) Field
        @Json(name = "ama_field") AMAField,
        @Json(name = "city") City,
        @Json(name = "controlled_airspace") ControlledAirspace,
        @Json(name = "country") Country,
        @Json(name = "county") County,
        @Json(name = "custom") Custom,
        @Json(name = "embassy") Embassy,
        @Json(name = "emergency") Emergency,
        @Json(name = "federal_building") FederalBuilding,

        // Flight Information Region
        @Json(name = "fir") FIR,
        @Json(name = "fire") Fire,
        @Json(name = "gliderport") Gliderport,
        @Json(name = "hazard_area") HazardArea,
        @Json(name = "heliport") Heliport,
        @Json(name = "highway") Highway,
        @Json(name = "hospital") Hospital,
        @Json(name = "industrial_property") IndustrialProperty,
        @Json(name = "jpn_base") JapanBase,
        @Json(name = "laanc") LAANC,
        @Json(name = "landing_site") LandingSite,
        @Json(name = "military_property") MilitaryProperty,

        // Notice to Airmen
        @Json(name = "notam") NOTAM,
        @Json(name = "notification") Notification,
        @Json(name = "obstacle") Obstacle,

        // National Security UAS Flight Restriction
        @Json(name = "nsufr") NSUFR,
        @Json(name = "park") Park,
        @Json(name = "police_station") PoliceStation,
        @Json(name = "power_plant") PowerPlant,
        @Json(name = "powerline") Powerline,
        @Json(name = "prison") Prison,
        @Json(name = "railway") Railway,
        @Json(name = "recreational_area") RecreationalArea,
        @Json(name = "residential_property") ResidentialProperty,
        @Json(name = "school") School,
        @Json(name = "seaplane_base") SeaplaneBase,
        @Json(name = "special_use_airspace") SpecialUse,
        @Json(name = "stadium") Stadium,
        @Json(name = "state") State,
        @Json(name = "subprefecture") Subprefecture,
        @Json(name = "supercity") Supercity,

        // Temporary Flight Restriction
        @Json(name = "tfr") TFR,

        // Terminal Maneuvering Area
        @Json(name = "tma") TMA,
        @Json(name = "ulm_field") UltralightField,
        @Json(name = "university") University,
        @Json(name = "waterway") Waterway,
        @Json(name = "wildfire") Wildfire,

        // Fallback for new airspace types not yet supported
        Unknown,
    }

    @JsonClass(generateAdapter = true)
    data class Status(
        @Json(name = "color") val color: Color,
        @Json(name = "advisories") val advisories: List<Advisory> = listOf(),
    ) {
        enum class Color {
            @Json(name = "red") Red,
            @Json(name = "orange") Orange,
            @Json(name = "yellow") Yellow,
            @Json(name = "green") Green,
        }
    }
}

@JsonClass(generateAdapter = true)
data class Advisory(
    @Json(name = "id") val id: String,
    @Json(name = "name") val name: String,
    @Json(name = "type") val type: Airspace.Type,
    @Json(name = "color") val color: Airspace.Status.Color,
    @Json(name = "latitude") val latitude: Double,
    @Json(name = "longitude") val longitude: Double,
    @Json(name = "rule_id") val ruleId: Int?,
    @Json(name = "ruleset_id") val rulesetId: String?,
    @Json(name = "country") val country: String,
    @Json(name = "state") val state: String?,
    @Json(name = "city") val city: String?,
    @Json(name = "last_updated") val lastUpdated: Date?,
    @Json(name = "distance") val distance: Int?, // TODO: What is the unit of this?
    @Json(name = "requirements") val requirements: Requirements?,
    @Json(name = "properties") val properties: AirspaceProperties?,
    // @Json(name = "timesheets") val timeSheets: List<TimeSheet>?,
)

@JsonClass(generateAdapter = true)
data class Requirements(
    @Json(name = "notice") val notice: Notice,
)

@JsonClass(generateAdapter = true)
data class Notice(
    @Json(name = "digital") val digital: Boolean,
    @Json(name = "phone") val phone: String?,
)

/// Network Request Models

@JsonClass(generateAdapter = true)
data class AdvisoriesRequest(
    @Json(name = "rulesets") val rulesetIds: String,
    @Json(name = "geometry") val geometry: Geometry,
    @Json(name = "start") val start: Date? = null,
    @Json(name = "end") val end: Date? = null,
)
