package com.airmap.airmapsdk.models

import com.mapbox.mapboxsdk.geometry.LatLng

// TODO: Custom Adapters
// TODO: https://proandroiddev.com/moshi-polymorphic-adapter-is-d25deebbd7c5
sealed class Geometry
data class Point(val coordinate: LatLng): Geometry()
data class Path(val coordinates: List<LatLng>): Geometry()
data class Polygon(val coordinates: List<List<LatLng>>): Geometry()
