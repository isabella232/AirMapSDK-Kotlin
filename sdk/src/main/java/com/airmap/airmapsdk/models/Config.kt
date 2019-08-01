package com.airmap.airmapsdk.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Config(
    val airmap: AirMapConfig,
    val mapbox: MapboxConfig,
    val app: AppConfig?,
    val droneInsurance: DroneInsuranceConfig?
)

@JsonClass(generateAdapter = true)
data class AirMapConfig(
    @Json(name = "api_key") val apiKey: String,
    @Json(name = "client_id") val clientId: String,
    val domain: String = "api.airmap.com",
    val environment: String?,
    @Json(name = "map_style") val mapStyle: String?
)

@JsonClass(generateAdapter = true)
data class AppConfig(@Json(name = "intro_images") val introImages: List<String>)

@JsonClass(generateAdapter = true)
data class MapboxConfig(@Json(name = "access_token") val accessToken: String)

@JsonClass(generateAdapter = true)
data class DroneInsuranceConfig(@Json(name = "app_id") val appId: String, val api: String, val frontend: String)