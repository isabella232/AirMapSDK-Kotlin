package com.airmap.airmapsdk.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Manufacturer(val id: String, val name: String, val url: String?)

@JsonClass(generateAdapter = true)
data class Model(val id: String, val name: String, val manufacturer: Manufacturer)

@JsonClass(generateAdapter = true)
data class Aircraft(val id: String, val nickname: String, val model: Model)