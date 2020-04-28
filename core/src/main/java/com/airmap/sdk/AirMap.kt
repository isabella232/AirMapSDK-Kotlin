package com.airmap.sdk

import com.airmap.sdk.clients.AirMapClient
import com.airmap.sdk.models.Config
import com.airmap.sdk.models.GeometryJsonAdapterFactory
import com.airmap.sdk.networking.AirMapCallAdapterFactory
import com.airmap.sdk.networking.CommaSeparatedConverterFactory
import com.airmap.sdk.networking.DateConverterFactory
import com.aungkyawpaing.geoshi.adapter.GeoshiJsonAdapterFactory
import com.serjltt.moshi.adapters.DeserializeOnly
import com.serjltt.moshi.adapters.FallbackEnum
import com.serjltt.moshi.adapters.Wrapped
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import okhttp3.CertificatePinner
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import java.util.Date

/**
 * The AirMap API gives you access to a wide range of airspace services to use in your app or device
 * Before you can use the AirMap API you will need to create a free AirMap account and apply to
 * become a developer. If approved you will be given an API key which is required for most API calls
 * and to use the SDK.
 */
object AirMap {
    @JvmStatic lateinit var client: AirMapClient
        private set
    @JvmStatic var userId: String? = null
    @JvmStatic lateinit var config: Config
        private set
    @JvmStatic var authToken: String? = null
    internal val urlPrefix by lazy {
        if (config.airmap.environment.isNullOrBlank()) {
            ""
        } else {
            "${config.airmap.environment}."
        }
    }

    /**
     * Initialize the SDK with the given [config]. This must be called first, before any other
     * functionality can be used. Optionally, [enableCertificatePinning] for a secure connection
     * or provide a custom [okHttpClientBuilder] to handle network connections in a special way.
     *
     * Once initialized, [client] can be used to make HTTP calls
     */
    @JvmOverloads
    @JvmStatic
    fun init(
        config: Config,
        enableCertificatePinning: Boolean = false,
        okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder(),
    ) {
        this.config = config
        val moshi = Moshi.Builder()
            .add(Wrapped.ADAPTER_FACTORY) // This needs to be the first adapter added to Moshi
            .add(DeserializeOnly.ADAPTER_FACTORY)
            .add(FallbackEnum.ADAPTER_FACTORY)
            .add(GeoshiJsonAdapterFactory())
            .add(GeometryJsonAdapterFactory())
            .add(Date::class.java, Rfc3339DateJsonAdapter().nullSafe())
            .build()

        val okHttpClient = okHttpClientBuilder.apply {
            if (enableCertificatePinning) {
                // TODO: Add other hosts
                val host = "api.airmap.com"
                val hostJP = "api.airmap.jp"
                certificatePinner(
                    CertificatePinner.Builder()
                        .add(host, "sha256/47DEQpj8HBSa+/TImW+5JCeuQeRkm5NMpJWZG3hSuFU=")
                        .add(host, "sha256/CJlvFGiErgX6zPm0H+oO/TRbKOERdQOAYOs2nUlvIQ0=")
                        .add(host, "sha256/8Rw90Ej3Ttt8RRkrg+WYDS9n7IS03bk5bjP/UXPtaY8=")
                        .add(host, "sha256/Ko8tivDrEjiY90yGasP6ZpBU4jwXvHqVvQI0GS3GNdA=")
                        .add(hostJP, "sha256/W5rhIQ2ZbJKFkRvsGDwQVS/H/NSixP33+Z/fpJ0O25Q=")
                        .build()
                )
            }
            // API Key Interceptor
            addInterceptor { it.addHeaderToRequest("X-API-Key", config.airmap.apiKey) }

            // Auth Token Interceptor - This needs to be added even if the token doesn't have a
            // value yet (because the authToken will get set when the user logs in)
            addInterceptor {
                when {
                    // TODO: Login logic (Add a listener when auth token is blank for apps to be
                    //  able to consume?)
                    authToken.isNullOrBlank() -> it.proceed(it.request())
                    else -> it.addHeaderToRequest("Authorization", "Bearer $authToken")
                }
            }
        }.build()

        client = AirMapClient(
            getClient("auth", 1, okHttpClient, moshi),
            getClient("aircraft", 2, okHttpClient, moshi),
            getClient("pilot", 2, okHttpClient, moshi),
            getClient("flight", 2, okHttpClient, moshi),
            getClient("airspace", 2, okHttpClient, moshi),
            getClient("rules", 1, okHttpClient, moshi),
            getClient("advisory", 1, okHttpClient, moshi),
            getClient("system", 1, okHttpClient, moshi),
        )
    }

    /**
     * Convenience method to simplify the verbose syntax of using an Interceptor to add a Header
     * Sets the header [name] to [value]
     */
    private fun Interceptor.Chain.addHeaderToRequest(name: String, value: String): Response {
        return this.proceed(this.request().newBuilder().addHeader(name, value).build())
    }

    /**
     * Returns a client of interface [T] for the given [serviceName] and [serviceVersion]. The
     * client will be backed by [client] for network requests and [moshi] for Object-JSON
     * serialization and deserialization
     */
    private inline fun <reified T> getClient(
        serviceName: String,
        serviceVersion: Int,
        client: OkHttpClient,
        moshi: Moshi,
    ): T {
        val baseUrl = HttpUrl.Builder()
            .scheme("https")
            .host("${urlPrefix}api.${config.airmap.domain}")
            .addPathSegments("$serviceName/v$serviceVersion/")
            .toString()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(CommaSeparatedConverterFactory())
            .addConverterFactory(DateConverterFactory())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(AirMapCallAdapterFactory())
            .client(client)
            .build()
            .create()
    }
}
