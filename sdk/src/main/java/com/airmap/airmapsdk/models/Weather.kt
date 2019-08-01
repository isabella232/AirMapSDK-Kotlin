package com.airmap.airmapsdk.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.LocalDateTime

@JsonClass(generateAdapter = true)
data class Wind(
    val heading: Int,
    val speed: Int,
    val gusting: Int
)

@JsonClass(generateAdapter = true)
data class WeatherUpdate(
    val time: LocalDateTime,
    val timezone: String,
    val condition: String,
    val icon: String,
    val wind: Wind,
    val humidity: Double,
    val visibility: Double,
    val precipitation: Double,
    val temperature: Double,
    @Json(name = "dew_point") val dewPoint: Double,
    val mslp: Double
)

@JsonClass(generateAdapter = true)
data class Forecast(val weather: List<WeatherUpdate>)
