package com.airmap.airmapsdk.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Airspace(
    @Json(name = "id") val id: String,
    @Json(name = "name") val name: String,
    @Json(name = "type") val type: Type,
    @Json(name = "country") val country: String,
    @Json(name = "state") val state: String,
    @Json(name = "city") val city: String,
    @Json(name = "geometry") val geometry: Geometry,
    @Json(name = "propertyBoundary") val propertyBoundary: Geometry // TODO: this is found at related_geometry.property_boundary.geometry
) {
//    TODO: @JsonClass(generateAdapter = true)
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
