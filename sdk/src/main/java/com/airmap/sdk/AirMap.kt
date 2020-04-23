package com.airmap.sdk

import com.airmap.sdk.clients.AirMapClient
import com.airmap.sdk.models.Config
import com.airmap.sdk.models.GeometryJsonAdapterFactory
import com.airmap.sdk.networking.AirMapCallAdapterFactory
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
import java.util.concurrent.TimeUnit

object AirMap {
    @JvmStatic lateinit var client: AirMapClient
        private set
    @JvmStatic var userId: String? = null
        private set
    private lateinit var config: Config
    private var authToken: String? = null

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
            connectTimeout(15, TimeUnit.SECONDS)
            readTimeout(15, TimeUnit.SECONDS)
            // API Key Interceptor
            addInterceptor { it.addHeaderToRequest("X-API-Key", config.airmap.apiKey) }

            // Auth Token Interceptor - We always add the interceptor regardless of if the token has a value yet. We
            // check inside the Interceptor for a token value since the Interceptor itself is persistent
            addInterceptor {
                when {
                    // TODO: Login logic (Add a listener when auth token is blank for apps to be able to consume?)
                    authToken.isNullOrBlank() -> it.proceed(it.request())
                    else -> it.addHeaderToRequest("Authorization", "Bearer $authToken")
                }
            }
        }.build()

        client = AirMapClient(
            getClient("aircraft", 2, okHttpClient, moshi),
            getClient("pilot", 2, okHttpClient, moshi),
            getClient("flight", 2, okHttpClient, moshi),
            getClient("airspace", 2, okHttpClient, moshi),
            getClient("rules", 1, okHttpClient, moshi),
            getClient("advisory", 1, okHttpClient, moshi),
        )
    }

    /**
     * Convenience method to simplify the verbose syntax of using an Interceptor to add a Header
     */
    private fun Interceptor.Chain.addHeaderToRequest(name: String, value: String): Response {
        return this.proceed(this.request().newBuilder().addHeader(name, value).build())
    }

    /**
     * Instantiate a client for the given service name and version
     *
     * @param T The Client abstract class to instantiate
     * @param serviceName The service name as used in the API URL
     * @param v The API version to access
     * @param client OkHttpClient instance used to create the client
     * @param moshi Moshi instance used for JSON serialization and deserialization
     * @return
     */
    private inline fun <reified T> getClient(
        serviceName: String,
        v: Int,
        client: OkHttpClient,
        moshi: Moshi,
    ): T {
        val prefix =
            if (config.airmap.environment.isNullOrBlank()) "" else "${config.airmap.environment}."
        val baseUrl = HttpUrl.Builder()
            .scheme("https")
            .host("${prefix}api.${config.airmap.domain}")
            .addPathSegments("$serviceName/v$v/")
            .toString()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(AirMapCallAdapterFactory())
            .client(client)
            .build()
            .create()
    }
}
