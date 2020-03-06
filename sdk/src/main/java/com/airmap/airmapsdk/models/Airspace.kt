package com.airmap.airmapsdk.models

import com.aungkyawpaing.geoshi.model.Geometry
import com.squareup.moshi.*
import java.lang.NullPointerException
import java.lang.reflect.Type
import java.util.*

@JsonClass(generateAdapter = true)
data class Airspace(
    @Json(name = "id") val id: String,
    @Json(name = "airspace_uuid") val uuid: String?,
    @Json(name = "name") val name: String,
    @Json(name = "type") val type: Type,
    @Json(name = "country") val country: String,
    @Json(name = "state") val state: String,
    @Json(name = "city") val city: String,
    @Json(name = "geometry") val geometry: Geometry,
    // TODO: dot notation!
    @Json(name = "related_geometry.property_boundary.geometry") val propertyBoundary: Geometry?
) {
    // Not every Airspace Type has properties
    enum class Type(val propertiesClass: Class<out AirspaceProperties>? = null) {
        @Json(name = "airport") Airport(AirportProperties::class.java),
        @Json(name = "ama_field") AMAField,
        @Json(name = "city") City,
        @Json(name = "controlled_airspace") ControlledAirspace(ControlledAirspaceProperties::class.java),
        @Json(name = "country") Country,
        @Json(name = "county") County,
        @Json(name = "custom") Custom,
        @Json(name = "embassy") Embassy,
        @Json(name = "emergency") Emergency(EmergencyProperties::class.java),
        @Json(name = "federal_building") FederalBuilding,
        @Json(name = "fir") FIR,
        @Json(name = "fire") Fire(FireProperties::class.java),
        @Json(name = "gliderport") Gliderport,
        @Json(name = "hazard_area") HazardArea,
        @Json(name = "heliport") Heliport(HeliportProperties::class.java),
        @Json(name = "highway") Highway,
        @Json(name = "hospital") Hospital,
        @Json(name = "industrial_property") IndustrialProperty,
        @Json(name = "jpn_base") JapanBase,
        @Json(name = "landing_site") LandingSite,
        @Json(name = "military_property") MilitaryProperty,
        @Json(name = "notam") NOTAM(NOTAMProperties::class.java),
        @Json(name = "notification") Notification(NotificationProperties::class.java),
        @Json(name = "park") Park(ParkProperties::class.java),
        @Json(name = "police_station") PoliceStation,
        @Json(name = "power_plant") PowerPlant(PowerPlantProperties::class.java),
        @Json(name = "powerline") Powerline,
        @Json(name = "prison") Prison,
        @Json(name = "railway") Railway,
        @Json(name = "recreational_area") RecreationalArea,
        @Json(name = "residential_property") ResidentialProperty,
        @Json(name = "school") School(SchoolProperties::class.java),
        @Json(name = "seaplane_base") SeaplaneBase,
        @Json(name = "special_use_airspace") SpecialUse(SpecialUseProperties::class.java),
        @Json(name = "stadium") Stadium,
        @Json(name = "state") State,
        @Json(name = "subprefecture") Subprefecture,
        @Json(name = "supercity") Supercity,
        @Json(name = "tfr") TFR(TFRProperties::class.java),
        @Json(name = "ulm_field") UltralightField,
        @Json(name = "university") University,
        @Json(name = "waterway") Waterway,
        @Json(name = "wildfire") Wildfire(FireProperties::class.java),
    }

    @JsonClass(generateAdapter = true)
    data class Status(
        @Json(name = "color") val color: Color,
        @Json(name = "advisories") val advisories: List<Advisory> = mutableListOf()
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
    @Transient val properties: AirspaceProperties? = null
)

class AdvisoryJsonAdapterFactory : JsonAdapter.Factory {
    override fun create(type: Type, annotations: Set<Annotation>, moshi: Moshi): JsonAdapter<Advisory>? {
        if (!Types.getRawType(type).isAssignableFrom(Advisory::class.java)) {
            return null
        }

        val adapter = moshi.nextAdapter<Advisory>(this, type, annotations)

        return object : JsonAdapter<Advisory>() {
            override fun toJson(writer: JsonWriter, value: Advisory?) = adapter.toJson(writer, value)
            override fun fromJson(reader: JsonReader): Advisory? {
                val peekedJson = reader.peekJson()
                val advisory = adapter.fromJson(peekedJson)

                @Suppress("UNCHECKED_CAST")
                val value = reader.readJsonValue() as Map<String, Any>? ?: return advisory
                val propertiesClass = advisory?.type?.propertiesClass
                return advisory?.copy(
                    properties = moshi.adapter(propertiesClass).nullSafe().fromJsonValue(value["properties"])
                )
            }
        }
    }
}

@JsonClass(generateAdapter = true)
data class Requirements(
    @Json(name = "notice") val notice: Notice
)

@JsonClass(generateAdapter = true)
data class Notice(
    @Json(name = "digital") val digital: Boolean,
    @Json(name = "phone") val phone: String
)

