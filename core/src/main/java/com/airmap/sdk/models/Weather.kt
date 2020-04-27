package com.airmap.sdk.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.Date

@JsonClass(generateAdapter = true)
data class Forecast(
    @Json(name = "weather") val weather: List<WeatherUpdate> = listOf(),
    @Json(name = "attribution") val attribution: String?,
    @Json(name = "attribution_uri") val attributionUri: String?,
)

@JsonClass(generateAdapter = true)
data class WeatherUpdate(
    @Json(name = "time") val time: Date,
    @Json(name = "timezone") val timezone: String,
    @Json(name = "condition") val condition: String, // Textual description (e.g. "Sunny")
    @Json(name = "icon") val icon: String?,
    @Json(name = "wind") val wind: Wind?,
    @Json(name = "humidity") val humidity: Percent?,
    @Json(name = "visibility") val visibility: Kilometers?,
    @Json(name = "precipitation") val precipitation: Percent?, // Probability of precipitation
    @Json(name = "temperature") val temperature: Celsius?,
    @Json(name = "dew_point") val dewPoint: Celsius?,
    @Json(name = "mslp") val pressure: HPa?, // Mean Sea Level Pressure
)

@JsonClass(generateAdapter = true)
data class Wind(
    @Json(name = "heading") val heading: Degrees?,
    @Json(name = "speed") val speed: MetersPerSecond?,
    @Json(name = "gusting") val gusting: MetersPerSecond?,
)
