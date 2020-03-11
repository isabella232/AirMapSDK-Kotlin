package com.airmap.airmapsdk.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.Date

@JsonClass(generateAdapter = true)
data class Wind(
    @Json(name = "heading") val heading: Int,
    @Json(name = "speed") val speed: Int,
    @Json(name = "gusting") val gusting: Int
)

@JsonClass(generateAdapter = true)
data class WeatherUpdate(
    @Json(name = "time") val time: Date,
    @Json(name = "timezone") val timezone: String,
    @Json(name = "condition") val condition: String,
    @Json(name = "icon") val icon: String,
    @Json(name = "wind") val wind: Wind,
    @Json(name = "humidity") val humidity: Double,
    @Json(name = "visibility") val visibility: Double,
    @Json(name = "precipitation") val precipitation: Double,
    @Json(name = "temperature") val temperature: Double,
    @Json(name = "dew_point") val dewPoint: Double,
    @Json(name = "coordinate") val mslp: Double
)

@JsonClass(generateAdapter = true)
data class Forecast(
    @Json(name = "weather") val weather: List<WeatherUpdate> = mutableListOf()
)
