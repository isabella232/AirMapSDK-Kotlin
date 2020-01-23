package com.airmap.airmapsdk.models

import com.mapbox.mapboxsdk.geometry.LatLng
import com.squareup.moshi.Json

// TODO: Custom Adapters
// TODO: https://proandroiddev.com/moshi-polymorphic-adapter-is-d25deebbd7c5

sealed class Geometry

data class Point(
    @Json(name = "coordinate") val coordinate: LatLng
) : Geometry()

data class Path(
    @Json(name = "coordinates") val coordinates: List<LatLng>
) : Geometry()

data class Polygon(
    @Json(name = "coordinates") val coordinates: List<List<LatLng>>
) : Geometry()
