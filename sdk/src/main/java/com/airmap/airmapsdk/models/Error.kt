package com.airmap.airmapsdk.models

import com.squareup.moshi.Json

data class Error(
    @Json(name = "name") val name: String?,
    @Json(name = "message") val message: String?
)
