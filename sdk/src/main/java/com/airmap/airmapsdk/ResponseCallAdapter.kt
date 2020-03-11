package com.airmap.airmapsdk

import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

// Based on: https://github.com/ebi-igweze/simple-call-adapter
class Response<R>(private val call: Call<R>) {

    /**
     * Java compatibility
     */
    fun execute(responseHandler: ResponseHandler<R?>) {
        execute(responseHandler::onResult)
    }

    fun execute(responseHandler: (R?, Throwable?) -> Unit) {
        call.enqueue(object : Callback<R> {
            override fun onFailure(call: Call<R>?, t: Throwable?) {
                responseHandler(null, t)
            }

            override fun onResponse(call: Call<R>?, response: Response<R>?) {
                when {
                    response?.isSuccessful == true -> responseHandler(response.body(), null)
                    response?.code() in 400..511 -> responseHandler(null, HttpException(response))
                    else -> responseHandler(response?.body(), null)
                }
            }
        })
    }
}

class AirMapResponseCallAdapter<R>(private val responseType: Type) : CallAdapter<R, Any> {
    override fun responseType(): Type = responseType
    override fun adapt(call: Call<R>): Any = Response(call)
}

class AirMapResponseCallAdapterFactory : CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        // FIXME: CAUTION - we are not doing any checks here!!
        return AirMapResponseCallAdapter<Any>(
            getParameterUpperBound(
                0,
                returnType as ParameterizedType
            )
        )

        // ensure enclosing type is 'Response'
        // TODO: Figure out why this (below) isn't working in the original way
//            val enclosingType = (it as ParameterizedType)
//            if (getRawType(returnType) != Response::class.java) null
//            else AirMapResponseCallAdapter<Any>(getParameterUpperBound(0, enclosingType))
    }
}

interface ResponseHandler<R> {
    fun onResult(response: R?, throwable: Throwable?)
}
