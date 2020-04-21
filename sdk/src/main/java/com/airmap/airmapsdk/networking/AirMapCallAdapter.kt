package com.airmap.airmapsdk.networking

import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class AirMapCallAdapter<R>(private val responseType: Type) : CallAdapter<R, Any> {
    override fun responseType() = responseType
    override fun adapt(call: Call<R>) = AirMapCall(call)
}

class AirMapCallAdapterFactory : CallAdapter.Factory() {
    override fun get(
        returnType: Type, annotations: Array<Annotation>, retrofit: Retrofit,
    ): CallAdapter<*, *>? = if (getRawType(returnType) != AirMapCall::class.java) null
    else AirMapCallAdapter<Any>(getParameterUpperBound(0, returnType as ParameterizedType))
}
