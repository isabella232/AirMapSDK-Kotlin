package com.airmap.sdk.models

import com.serjltt.moshi.adapters.FallbackEnum
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.Date

@JsonClass(generateAdapter = true)
data class Status(
    @Json(name = "components") val components: List<Component>?,
    @Json(name = "level") val level: Level?,
) {
    @JsonClass(generateAdapter = true)
    data class Component(
        @Json(name = "id") val id: String,
        @Json(name = "name") val name: String?,
        @Json(name = "level") val level: Level?,
        @Json(name = "children") val children: List<Component>?,
        @Json(name = "updated_at") val updatedAt: Date?,
    )

    @FallbackEnum(name = "Unknown")
    enum class Level {
        @Json(name = "degraded") Degraded,
        @Json(name = "failed") Failed,
        @Json(name = "maintenance") Maintenance,
        @Json(name = "normal") Normal,
        @Json(name = "unknown") Unknown,
    }
}
