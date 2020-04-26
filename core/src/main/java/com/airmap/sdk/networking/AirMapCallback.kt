package com.airmap.sdk.networking

/**
 * Asynchronously receive the [Result] of an HTTP call with details of either [Result.success] or
 * [Result.failure]
 */
fun interface AirMapCallback<R> {
    fun onResult(result: Result<R>)
}
