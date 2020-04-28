package com.airmap.sdk.clients

import com.airmap.sdk.models.Status
import com.airmap.sdk.networking.AirMapCall
import com.serjltt.moshi.adapters.Wrapped
import retrofit2.http.GET

interface SystemClient {
    /**
     * Check the [Status] of AirMap System Services
     */
    @GET("status")
    @Wrapped(path = ["data"])
    fun getSystemStatus(): AirMapCall<Status>
}
