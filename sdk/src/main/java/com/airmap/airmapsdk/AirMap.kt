package com.airmap.airmapsdk

import com.airmap.airmapsdk.clients.*
import com.airmap.airmapsdk.models.Config
import com.airmap.airmapsdk.models.Flight
import com.airmap.airmapsdk.models.VerificationRequest
import com.aungkyawpaing.geoshi.adapter.GeoshiJsonAdapterFactory
import com.aungkyawpaing.geoshi.model.LineString
import com.aungkyawpaing.geoshi.model.Point
import com.aungkyawpaing.geoshi.model.Polygon
import com.serjltt.moshi.adapters.Wrapped
import com.squareup.moshi.Moshi
import okhttp3.*
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import timber.log.Timber

object AirMap {
    lateinit var client: AirMapClient
    private lateinit var config: Config
    var userId: String? = null // "auth0|5761a4279732f5844b1db844"
    private var authToken: String? = null
    private val certificatePinner: CertificatePinner
        get() {
            val host = "api.airmap.com"
            val hostJP = "api.airmap.jp"
            // TODO: Add other hosts?
            return CertificatePinner.Builder()
                .add(host, "sha256/47DEQpj8HBSa+/TImW+5JCeuQeRkm5NMpJWZG3hSuFU=")
                .add(host, "sha256/CJlvFGiErgX6zPm0H+oO/TRbKOERdQOAYOs2nUlvIQ0=")
                .add(host, "sha256/8Rw90Ej3Ttt8RRkrg+WYDS9n7IS03bk5bjP/UXPtaY8=")
                .add(host, "sha256/Ko8tivDrEjiY90yGasP6ZpBU4jwXvHqVvQI0GS3GNdA=")
                .add(hostJP, "sha256/W5rhIQ2ZbJKFkRvsGDwQVS/H/NSixP33+Z/fpJ0O25Q=")
                .build()
        }

    fun init(config: Config, enableCertificatePinning: Boolean = false) {
        Timber.plant(Timber.DebugTree())
        this.config = config
        val moshi = Moshi.Builder()
            .add(Wrapped.ADAPTER_FACTORY) // This needs to be the first adapter added to Moshi
            .add(GeoshiJsonAdapterFactory())
            .build()

        val okHttpClient = OkHttpClient.Builder().apply {
            if (enableCertificatePinning) certificatePinner(certificatePinner)

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
            getClient("advisory", 1, okHttpClient, moshi)
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
}

// The clients represent the raw REST API. Any extra methods defined in AirMapClient are convenience
class AirMapClient(
    private val aircraftClient: AircraftClient,
    private val pilotClient: PilotClient,
    private val flightClient: FlightClient,
    private val airspaceClient: AirspaceClient,
    private val rulesClient: RulesClient,
    private val advisoryClient: AdvisoryClient
) : AircraftClient by aircraftClient,
    PilotClient by pilotClient,
    FlightClient by flightClient,
    AirspaceClient by airspaceClient,
    RulesClient by rulesClient,
    AdvisoryClient by advisoryClient
{
    // TODO: look into implications of moving this into the respective clients themselves
    fun verifySMS(token: String) = verifySMS(VerificationRequest(token))
    fun createFlight(flight: Flight) = when (flight.geometry) {
        is Point -> createFlightPoint(flight)
        is LineString -> createFlightPath(flight)
        is Polygon -> createFlightPolygon(flight)
        else -> throw Exception("Flight geometry was null or an unsupported type")
    }

    // getPublicFlights (make use of getFlights with custom parameters)
}
