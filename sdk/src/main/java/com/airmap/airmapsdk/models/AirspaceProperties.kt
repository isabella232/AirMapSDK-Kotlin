package com.airmap.airmapsdk.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonClass
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import java.util.Date

// TODO: Verify all these fields, Swift SDK has different fields...
sealed class AirspaceProperties {
    companion object {
        val jsonAdapterFactory: JsonAdapter.Factory =
            PolymorphicJsonAdapterFactory.of(AirspaceProperties::class.java, "type")
                .withSubtype(AirportProperties::class.java, Airspace.Type.Airport.apiName)
                .withSubtype(AMAFieldProperties::class.java, Airspace.Type.AMAField.apiName)
                .withSubtype(
                    ControlledAirspaceProperties::class.java,
                    Airspace.Type.ControlledAirspace.apiName
                )
                .withSubtype(EmergencyProperties::class.java, Airspace.Type.Emergency.apiName)
                .withSubtype(FireProperties::class.java, Airspace.Type.Fire.apiName)
                .withSubtype(HeliportProperties::class.java, Airspace.Type.Heliport.apiName)
                .withSubtype(NOTAMProperties::class.java, Airspace.Type.NOTAM.apiName)
                .withSubtype(NotificationProperties::class.java, Airspace.Type.Notification.apiName)
                .withSubtype(ParkProperties::class.java, Airspace.Type.Park.apiName)
                .withSubtype(PowerPlantProperties::class.java, Airspace.Type.PowerPlant.apiName)
                .withSubtype(SchoolProperties::class.java, Airspace.Type.School.apiName)
                .withSubtype(SpecialUseProperties::class.java, Airspace.Type.SpecialUse.apiName)
                .withSubtype(TFRProperties::class.java, Airspace.Type.TFR.apiName)
                .withSubtype(FireProperties::class.java, Airspace.Type.Wildfire.apiName)
                .withDefaultValue(null)
    }
}

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
data class AMAFieldProperties(
    @Json(name = "authorization") val authorization: Boolean,
    @Json(name = "laanc") val laanc: Boolean,
    @Json(name = "url") val url: String?
) : AirspaceProperties()

@JsonClass(generateAdapter = true)
data class ControlledAirspaceProperties(
    @Json(name = "url") val url: String?,
    @Json(name = "icao") val icao: String?,
    @Json(name = "laanc") val laanc: Boolean = false,
    @Json(name = "ceiling") val ceiling: Int?,
    @Json(name = "airport_id") val airportId: String?,
    @Json(name = "airport_name") val airportName: String?,
    @Json(name = "lowest_limit") val lowestLimit: Int?,
    @Json(name = "authorization") val authorization: Boolean = false,
    // TODO: This does not come back as ISO 8601 (comes back as 10/10/2020). How to parse this into a Date?
    @Json(name = "last_edit_date") val lastEditDate: String?,
    @Json(name = "description") val description: String?,
    @Json(name = "airspace_class") val airspaceClass: String?,
    @Json(name = "airspace_classification") val airspaceClassification: String?,
    @Json(name = "facility_id") val facilityId: String?

) : AirspaceProperties()

@JsonClass(generateAdapter = true)
data class EmergencyProperties(
    @Json(name = "url") val url: String?,
    @Json(name = "description") val description: String?,
    @Json(name = "agency_id") val agencyId: String?,
    @Json(name = "date_effective") val dateEffective: Date?
) : AirspaceProperties()

@JsonClass(generateAdapter = true)
data class FireProperties(
    @Json(name = "url") val url: String?,
    @Json(name = "description") val description: String?,
    @Json(name = "size") val size: Int?,
    @Json(name = "date_effective") val dateEffective: Date?
) : AirspaceProperties()

@JsonClass(generateAdapter = true)
data class HeliportProperties(
    @Json(name = "url") val url: String?,
    @Json(name = "description") val description: String?,
    @Json(name = "phone") val phone: String?,
    @Json(name = "use") val use: String?
) : AirspaceProperties()

@JsonClass(generateAdapter = true)
data class NOTAMProperties(
    @Json(name = "url") val url: String?,
    @Json(name = "description") val description: String?,
    @Json(name = "effective_start") val effectiveStart: Date?,
    @Json(name = "effective_end") val effectiveEnd: Date?
) : AirspaceProperties()

@JsonClass(generateAdapter = true)
data class NotificationProperties(
    @Json(name = "url") val url: String?,
    @Json(name = "description") val description: String?
) : AirspaceProperties()

@JsonClass(generateAdapter = true)
data class ParkProperties(
    @Json(name = "url") val url: String?,
    @Json(name = "description") val description: String?,
    @Json(name = "size") val size: Int?,
    @Json(name = "type") val type: String?
) : AirspaceProperties()

@JsonClass(generateAdapter = true)
data class PowerPlantProperties(
    @Json(name = "url") val url: String?,
    @Json(name = "description") val description: String?,
    @Json(name = "tech") val tech: String?,
    @Json(name = "plant_code") val plantCode: Int?
) : AirspaceProperties()

@JsonClass(generateAdapter = true)
data class SchoolProperties(
    @Json(name = "url") val url: String?,
    @Json(name = "description") val description: String?,
    @Json(name = "type") val type: String?,
    @Json(name = "building") val building: Boolean?,
    @Json(name = "way_area") val wayArea: Double?
) : AirspaceProperties()

@JsonClass(generateAdapter = true)
data class SpecialUseProperties(
    @Json(name = "url") val url: String?,
    @Json(name = "description") val description: String?,
    @Json(name = "currently_active") val currentlyActive: Boolean?
) : AirspaceProperties()

@JsonClass(generateAdapter = true)
data class TFRProperties(
    @Json(name = "url") val url: String?,
    @Json(name = "body") val body: String?,
    @Json(name = "description") val description: String?,
    @Json(name = "effective_start") val effectiveStart: Date?,
    @Json(name = "effective_end") val effectiveEnd: Date?
) : AirspaceProperties()
