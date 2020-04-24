package com.airmap.sdk.clients

import com.airmap.sdk.models.AnonymousLoginToken
import com.airmap.sdk.networking.AirMapCall
import com.serjltt.moshi.adapters.Wrapped
import retrofit2.http.Field
import retrofit2.http.POST

interface AuthClient {
    @POST("anonymous/token")
    @Wrapped(path = ["data"])
    fun getAnonymousToken(
        @Field("user_id") userId: String,
    ): AirMapCall<AnonymousLoginToken>
}
