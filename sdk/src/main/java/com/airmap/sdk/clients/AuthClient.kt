package com.airmap.sdk.clients

import com.airmap.sdk.models.AnonymousToken
import com.airmap.sdk.models.Token
import com.airmap.sdk.networking.AirMapCall
import com.serjltt.moshi.adapters.Wrapped
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Path

interface AuthClient {
    @POST("anonymous/token")
    @FormUrlEncoded
    @Wrapped(path = ["data"])
    fun getAnonymousToken(
        @Field("user_id") userId: String,
    ): AirMapCall<AnonymousToken>

    @POST("https://{prefix}auth.airmap.com/realms/airmap/protocol/openid-connect/token")
    @FormUrlEncoded
    fun getToken(
        @Path("prefix") urlPrefix: String,
        @Field("grant_type") grantType: String,
        @Field("client_id") clientId: String,
        @Field("username") username: String,
        @Field("password") password: String,
    ): AirMapCall<Token>

    @POST("https://{prefix}auth.airmap.com/realms/airmap/protocol/openid-connect/token")
    @FormUrlEncoded
    fun refreshToken(
        @Path("prefix") urlPrefix: String,
        @Field("grant_type") grantType: String,
        @Field("client_id") clientId: String,
        @Field("refresh_token") refreshToken: String,
    ): AirMapCall<Token>
}
