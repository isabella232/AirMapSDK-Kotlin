package com.airmap.airmapsdk.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Manufacturer(
    @Json(name = "id") val id: String,
    @Json(name = "name") val name: String,
    @Json(name = "url") val url: String?
)

@JsonClass(generateAdapter = true)
data class Model(
    @Json(name = "id") val id: String,
    @Json(name = "name") val name: String,
    @Json(name = "manufacturer") val manufacturer: Manufacturer,
    @Json(name = "metadata") val metadata: Map<String, Any?> = mutableMapOf()
)

@JsonClass(generateAdapter = true)
data class Aircraft(
    @Json(name = "id") val id: String,
    @Json(name = "nickname") val nickname: String?,
    @Json(name = "model") val model: Model
)
