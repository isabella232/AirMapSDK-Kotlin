package com.airmap.sdk.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AnonymousToken(
    @Json(name = "id_token") val idToken: String,
)

@JsonClass(generateAdapter = true)
data class Token(
    @Json(name = "access_token") val accessToken: String,
    @Json(name = "expires_in") val expiresIn: Seconds,
    @Json(name = "not-before-policy") val notBeforePolicy: Int,
    @Json(name = "refresh_expires_in") val refreshExpiresIn: Seconds,
    @Json(name = "refresh_token") val refreshToken: String,
    @Json(name = "scope") val scope: String,
    @Json(name = "session_state") val sessionState: String,
    @Json(name = "token_type") val tokenType: String,
    @Json(name = "id_token") val idToken: String?,
)
