package com.airmap.airmapsdk.networking

fun interface AirMapCallback<R> {
    fun onResult(result: Result<R>)
}
