package com.airmap.airmapsdk

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.airmap.airmapsdk.clients.AircraftClient
import com.airmap.airmapsdk.models.Config
import com.readystatesoftware.chuck.ChuckInterceptor
import com.serjltt.moshi.adapters.Wrapped
import com.squareup.moshi.Moshi
import okhttp3.CertificatePinner
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import timber.log.Timber
import java.io.Reader

object AirMap {
    lateinit var client: AirMapClient
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
                when {
                    // TODO: Login logic
                    authToken.isBlank() -> it.proceed(it.request())
                    else -> it.proceed(
                        it.request().newBuilder().addHeader("Authorization", "Bearer $authToken").build()
                    )
                }

            }
        }.build()

        val aircraftClient = getClient<AircraftClient>("aircraft", 2, okHttpClient, moshi)
        client = AirMapClient(aircraftClient)
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

class AirMapClient(private val aircraftClient: AircraftClient) : AircraftClient by aircraftClient
