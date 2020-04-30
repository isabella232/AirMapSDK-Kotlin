package com.airmap.sdk.networking

import com.airmap.sdk.models.ErrorDetails
import com.airmap.sdk.models.ServerError
import com.squareup.moshi.Moshi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * An interface to the raw HTTP call. Default operations (i.e. [clone] and [cancel]) are delegated
 * to the original [Call] provided by Retrofit. [AirMapCall] provides extra functionality to handle
 * AirMap specific errors, and provide an intermediate interface between different ways of
 * processing responses (e.g. [AirMapCallback]).
 */
class AirMapCall<R>(private val call: Call<R>) : Call<R> by call {
    /**
     * Synchronously execute the [request] and return the [Result] of the call as either a
     * [Result.success] or [Result.failure]
     */
    fun executeAirMap() = parseResponse(execute())

    /**
     * Asynchronously execute the [request] and provide the [Result] of the call as either a
     * [Result.success] or [Result.failure] via an [AirMapCallback]
     */
    fun enqueue(callback: AirMapCallback<R>) = enqueue(object : Callback<R> {
        override fun onFailure(call: Call<R>, t: Throwable) =
            callback.onResult(Result.failure(t))

        override fun onResponse(call: Call<R>, response: Response<R>) =
            callback.onResult(parseResponse(response))
    })

    private fun parseResponse(response: Response<R>): Result<R> {
        return if (response.isSuccessful) {
            try {
                Result.success(response.body()!!)
            } catch (e: Exception) {
                // Most likely a Moshi deserialization error. This was in all other senses a
                // successful operation, so no point of passing back the response code
                Result.failure(e)
            }
        } else {
            val raw = response.errorBody()?.string().orEmpty()
            try {
                val details = Moshi.Builder().build()
                    .adapter(ErrorDetails::class.java)
                    .nullSafe()
                    .lenient()
                    .fromJson(raw)!!
                Result.failure(ServerError(response.code(), details))
            } catch (e: Exception) {
                // Most likely a Moshi deserialization error. We have 2 errors now: ServerError and
                // e. The more important one to the consumer will be the ServerError, but we lose
                // track of e, then. Let's leave some form of breadcrumbs for this error by at least
                // printing a stack trace
                e.printStackTrace()
                Result.failure(ServerError(response.code(), ErrorDetails(raw = raw)))
            }
        }
    }

    // TODO: Add conversions here (e.g. coroutines, LiveData, Rx)
}
