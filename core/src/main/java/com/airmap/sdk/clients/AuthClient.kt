package com.airmap.sdk.clients

import com.airmap.sdk.AirMap
import com.airmap.sdk.models.AnonymousToken
import com.airmap.sdk.models.Token
import com.airmap.sdk.networking.AirMapCall
import com.serjltt.moshi.adapters.Wrapped
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Path

interface AuthClient {
    /**
     * Create an anonymous token that has permission to create flights. The token is identified by
     * [userId], which can be any unique identifier for the user (UUID, username, email, etc)
     */
    @POST("anonymous/token")
    @FormUrlEncoded
    @Wrapped(path = ["data"])
    fun getAnonymousToken(
        @Field("user_id") userId: String,
    ): AirMapCall<AnonymousToken>

    /**
     * Log in and authenticate a user by [username] and [password]. You must also provide your
     * application's [clientId]. The [grantType] here should always be "password"
     */
    @POST("https://{prefix}auth.airmap.com/realms/airmap/protocol/openid-connect/token")
    @FormUrlEncoded
    fun getToken(
        @Field("client_id") clientId: String,
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("scope") scope: String = "openid email profile offline_access am-api",
        @Field("grant_type") grantType: String = "password",
        @Path("prefix") urlPrefix: String = AirMap.urlPrefix,
    ): AirMapCall<Token>

    /**
     * Refresh a user's expiring auth token and get a new one by providing a [refreshToken]. You
     * must also provide your application's [clientId]. The [grantType] here should always be
     * "refresh_token". [urlPrefix] is used to access separate auth environments for testing
     */
    @POST("https://{prefix}auth.airmap.com/realms/airmap/protocol/openid-connect/token")
    @FormUrlEncoded
    fun refreshToken(
        @Field("client_id") clientId: String,
        @Field("refresh_token") refreshToken: String,
        @Field("grant_type") grantType: String = "refresh_token",
        @Path("prefix") urlPrefix: String = AirMap.urlPrefix,
    ): AirMapCall<Token>
}
