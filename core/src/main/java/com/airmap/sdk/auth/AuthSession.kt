package com.airmap.sdk.auth

import com.airmap.sdk.AirMap
import com.airmap.sdk.models.Seconds
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types.newParameterizedType
import java.util.Date

internal class AuthSession(
    private var clientId: String,
    authToken: String?,
    var refreshToken: String,
    internal var listener: AuthTokenListener? = null,
) {
    private var expiresAt: Date
    var authToken: String? = authToken
        set(value) {
            if (value != null) {
                expiresAt = getTokenExpiry(value)
                listener?.onTokenUpdated(value, refreshToken)
            }
        }

    init {
        this.authToken = authToken
        this.expiresAt = if (authToken == null) Date() else getTokenExpiry(authToken)
    }

    fun refreshIfExpired(leeway: Seconds = 0) {
        if (isExpired(leeway)) refresh()
    }

    fun isExpired(leeway: Seconds = 0): Boolean {
        // The refresh token never expires
        return authToken == null || expiresAt < Date()
    }

    fun refresh() {
        val result = AirMap.client.refreshToken(clientId, refreshToken).executeAirMap()
        result.onSuccess {
            refreshToken = it.refreshToken
            authToken = it.accessToken
        }
        result.onFailure { listener?.onTokenRefreshFail(it) }
    }

    private fun getTokenExpiry(token: String): Date {
        // See: https://jwt.io/ for JWT format
        val payload = token.split('.')[1]
        val type = newParameterizedType(Map::class.java, String::class.java, String::class.java)
        val moshi = Moshi.Builder().build()
        val adapter = moshi.adapter<Map<String, String>>(type)
        val jsonMap = adapter.fromJson(payload)!!
        val expAtEpochSeconds = jsonMap["exp"]?.toLongOrNull() ?: 0
        return Date(expAtEpochSeconds * 1000)
    }
}
