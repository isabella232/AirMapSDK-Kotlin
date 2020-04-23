package com.airmap.sdk.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

// TODO: Represent this as a Map instead so that it is extensible from the app?
@JsonClass(generateAdapter = true)
data class Config(
    @Json(name = "airmap") val airmap: AirMapConfig,
    @Json(name = "mapbox") val mapbox: MapboxConfig,
    @Json(name = "app") val app: AppConfig?,
    @Json(name = "drone_insurance") val droneInsurance: DroneInsuranceConfig?,
)

@JsonClass(generateAdapter = true)
data class AirMapConfig(
    @Json(name = "api_key") val apiKey: String,
    @Json(name = "client_id") val clientId: String,
    @Json(name = "domain") val domain: String = "airmap.com",
    @Json(name = "environment") val environment: String?,
    @Json(name = "map_style") val mapStyle: String?,
)

@JsonClass(generateAdapter = true)
data class AppConfig(
    @Json(name = "intro_images") val introImages: List<String>,
)

@JsonClass(generateAdapter = true)
data class MapboxConfig(
    @Json(name = "access_token") val accessToken: String,
)

@JsonClass(generateAdapter = true)
data class DroneInsuranceConfig(
    @Json(name = "app_id") val appId: String,
    @Json(name = "api") val api: String,
    @Json(name = "frontend") val frontend: String,
)
