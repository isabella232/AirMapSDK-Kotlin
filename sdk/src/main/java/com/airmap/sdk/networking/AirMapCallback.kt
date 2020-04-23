package com.airmap.sdk.networking

fun interface AirMapCallback<R> {
    fun onResult(result: Result<R>)
}
