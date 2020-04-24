package com.airmap.sdk.networking

import com.airmap.sdk.models.ServerError
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AirMapCall<R>(private val call: Call<R>) : Call<R> by call {

    fun enqueue(callback: AirMapCallback<R>) {
        enqueue(object : Callback<R> {
            override fun onFailure(call: Call<R>, t: Throwable) =
                callback.onResult(Result.failure(t))

            override fun onResponse(call: Call<R>, response: Response<R>) {
                if (response.isSuccessful) {
                    callback.onResult(Result.success(response.body()!!))
                } else {
                    val error = ServerError(response.errorBody()?.string().orEmpty())
                    callback.onResult(Result.failure(error))
                }
            }
        })
    }

    // TODO: Add conversions here (e.g. coroutines, LiveData, Rx)
}
