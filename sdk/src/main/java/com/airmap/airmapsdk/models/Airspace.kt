package com.airmap.airmapsdk.models

import com.aungkyawpaing.geoshi.model.Geometry
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
    enum class Type {
        @Json(name = "airport") Airport,
        @Json(name = "ama_field") AMAField,
        @Json(name = "city") City,
        @Json(name = "controlled_airspace") ControlledAirspace,
        @Json(name = "country") Country,
        @Json(name = "county") County,
        @Json(name = "custom") Custom,
        @Json(name = "embassy") Embassy,
        @Json(name = "emergency") Emergency,
        @Json(name = "federal_building") FederalBuilding,
        @Json(name = "fir") FIR,
        @Json(name = "fire") Fire,
        @Json(name = "gliderport") Gliderport,
        @Json(name = "hazard_area") HazardArea,
        @Json(name = "heliport") Heliport,
        @Json(name = "highway") Highway,
        @Json(name = "hospital") Hospital,
        @Json(name = "industrial_property") IndustrialProperty,
        @Json(name = "jpn_base") JapanBase,
        @Json(name = "military_property") MilitaryProperty,
        @Json(name = "notam") NOTAM,
        @Json(name = "notification") Notification,
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
        @Json(name = "tfr") TFR,
        @Json(name = "ulm_field") UltralightField,
        @Json(name = "university") University,
        @Json(name = "waterway") Waterway,
        @Json(name = "wildfire") Wildfire,
    }

    @JsonClass(generateAdapter = true)
    data class Status(
        @Json(name = "color") val color: Color,
        @Json(name = "advisories") val advisories: List<Advisory>
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
    @Json(name = "organization_id") val organizationId: String,
    @Json(name = "type") val type: Airspace.Type,
    @Json(name = "country") val country: String,
    @Json(name = "state") val state: String,
    @Json(name = "city") val city: String,
    @Json(name = "last_updated") val lastUpdated: Date,
    @Json(name = "color") val color: Airspace.Status.Color,
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

