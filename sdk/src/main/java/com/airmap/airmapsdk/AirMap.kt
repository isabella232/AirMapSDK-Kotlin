package com.airmap.airmapsdk

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.airmap.airmapsdk.clients.AircraftClient
import com.airmap.airmapsdk.models.Config
import com.readystatesoftware.chuck.ChuckInterceptor
import com.serjltt.moshi.adapters.Wrapped
import okhttp3.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import timber.log.Timber
import com.squareup.moshi.Moshi
import java.io.Reader


object AirMap {
    lateinit var aircraftClient: AircraftClient
    lateinit var preferences: SharedPreferences
    lateinit var config: Config

    private lateinit var authToken: String


    fun init(context: Context, enableCertificatePinning: Boolean = false) {
        Timber.plant(Timber.DebugTree())
        preferences = EncryptedSharedPreferences.create(
            "airmap_prefs",
            MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
        authToken = ""


        val moshi = Moshi.Builder().add(Wrapped.ADAPTER_FACTORY).build()
        config = getConfig(context, moshi)

        val okHttpClient = OkHttpClient.Builder().apply {
            if (enableCertificatePinning) certificatePinner(getCertificatePinner())
            addInterceptor(ChuckInterceptor(context))
            addInterceptor {
                it.proceed(it.request().newBuilder().addHeader("X-API-Key", config.airmap.apiKey).build())
            }
            addInterceptor {
                // Don't intercept if not logged in or refresh token not yet expired or request is for login/refresh
                // TODO: Don't intercept if matches login URL
                // TODO: Don't intercept if matches token refresh URL
                when {
                    authToken.isBlank() -> it.proceed(it.request())
//                    authToken.matchesRefreshUrl() -> it.proceed(it.request())
//                    authToken.matchesLoginUrl() -> it.proceed(it.request())
//                    authToken.isExpired -> refreshAuthToken()
                    else -> it.proceed(
                        it.request().newBuilder().addHeader("Authorization", "Bearer $authToken").build()
                    )
                }

            }
        }.build()

        aircraftClient = getClient("aircraft", 2, okHttpClient, moshi)
    }


    private fun getConfig(context: Context, moshi: Moshi) = try {
        moshi.adapter(Config::class.java).fromJson(
            context.resources.assets.open("airmap.config.json").reader().use(Reader::readText)
        )!!
    } catch (e: Exception) {
        throw RuntimeException("Please ensure your airmap.config.json file is in your /assets directory")
    }

    private inline fun <reified T> getClient(serviceName: String, v: Int, client: OkHttpClient, moshi: Moshi): T {
        val prefix = if (config.airmap.environment.isNullOrBlank()) "" else "${config.airmap.environment}."
        val baseUrl = HttpUrl.Builder()
            .scheme("https")
            .host("${prefix}api.${config.airmap.domain}")
            .addPathSegments("$serviceName/v$v/")
            .toString()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(AirMapResponseCallAdapterFactory())
            .client(client)
            .build()
            .create()
    }

    private fun getCertificatePinner(): CertificatePinner {
        val host = "api.airmap.com"
        val hostJP = "api.airmap.jp"
        return CertificatePinner.Builder()
            .add(host, "sha256/47DEQpj8HBSa+/TImW+5JCeuQeRkm5NMpJWZG3hSuFU=")
            .add(host, "sha256/CJlvFGiErgX6zPm0H+oO/TRbKOERdQOAYOs2nUlvIQ0=")
            .add(host, "sha256/8Rw90Ej3Ttt8RRkrg+WYDS9n7IS03bk5bjP/UXPtaY8=")
            .add(host, "sha256/Ko8tivDrEjiY90yGasP6ZpBU4jwXvHqVvQI0GS3GNdA=")
            .add(hostJP, "sha256/W5rhIQ2ZbJKFkRvsGDwQVS/H/NSixP33+Z/fpJ0O25Q=")
            .build()
    }
}