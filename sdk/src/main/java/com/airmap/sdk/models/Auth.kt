package com.airmap.sdk.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AnonymousLoginToken(
    @Json(name = "id_token") val idToken: String,
)
