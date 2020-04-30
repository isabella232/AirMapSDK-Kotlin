package com.airmap.sdk.auth

interface AuthTokenListener {
    fun onTokenUpdated(authToken: String, refreshToken: String)
    fun onTokenRefreshFail(throwable: Throwable)
}
