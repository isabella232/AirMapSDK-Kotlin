package com.airmap.sdk.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Comm(
    @Json(name = "key") val key: String,
)
