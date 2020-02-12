package com.airmap.airmapsdk

//import androidx.lifecycle.Lifecycle
//import androidx.lifecycle.Lifecycle.Event.*
//import androidx.lifecycle.LifecycleObserver
//import androidx.lifecycle.LifecycleOwner
//import androidx.lifecycle.OnLifecycleEvent
import retrofit2.*
import retrofit2.Response
import timber.log.Timber
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
//todo

//    fun execute(responseHandler: (R?, Throwable?) -> Unit): Subscription {
//        val subscription = Subscription()
//        call.enqueue(object : Callback<R> {
//            override fun onFailure(call: Call<R>?, t: Throwable?) {
//                if (!subscription.isDisposed()) responseHandler(null, t)
//            }
//
//            override fun onResponse(call: Call<R>?, response: Response<R>?) {
//                if (!subscription.isDisposed()) when {
//                    response?.isSuccessful == true -> responseHandler(response.body(), null)
//                    response?.code() in 400..511 -> responseHandler(null, HttpException(response))
//                    else -> responseHandler(response?.body(), null)
//                }
//            }
//        })
//        return subscription
//    }
}

class AirMapResponseCallAdapter<R>(private val responseType: Type) : CallAdapter<R, Any> {
    init {
        Timber.d("In here")
    }
    override fun responseType(): Type = responseType
    override fun adapt(call: Call<R>): Any = Response(call)
}

class AirMapResponseCallAdapterFactory : CallAdapter.Factory() {
    override fun get(returnType: Type, annotations: Array<Annotation>, retrofit: Retrofit): CallAdapter<*, *>? {
        // FIXME: CAUTION - we are not doing any checks here!!
        return AirMapResponseCallAdapter<Any>(getParameterUpperBound(0, returnType as ParameterizedType))

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

//class Subscription {
//    private var disposed = false
//    fun isDisposed() = disposed
//    fun dispose() {
//        disposed = true
//    }
//
//    fun bind(owner: LifecycleOwner) = bind(owner, ON_DESTROY)
//
//    fun bind(owner: LifecycleOwner, event: Lifecycle.Event) {
//
//        owner.lifecycle.addObserver(object : LifecycleObserver {
//
//            @OnLifecycleEvent(ON_PAUSE)
//            fun onPause() {
//                if (event == ON_PAUSE) {
//                    removeObserverAndDispose(owner)
//                }
//            }
//
//            @OnLifecycleEvent(ON_STOP)
//            fun onStop(owner: LifecycleOwner) {
//                if (event == ON_STOP) {
//                    removeObserverAndDispose(owner)
//                }
//            }
//
//            @OnLifecycleEvent(ON_DESTROY)
//            fun onDestroy(owner: LifecycleOwner) {
//                if (event == ON_DESTROY) {
//                    removeObserverAndDispose(owner)
//                }
//            }
//
//            fun removeObserverAndDispose(owner: LifecycleOwner) {
//                owner.lifecycle.removeObserver(this)
//                dispose()
//            }
//        })
//    }
//
//}
