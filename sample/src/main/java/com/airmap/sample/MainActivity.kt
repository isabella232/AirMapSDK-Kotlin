package com.airmap.sample

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.airmap.airmapsdk.AirMap
import com.airmap.airmapsdk.AirMap.client
import com.airmap.airmapsdk.Response
import com.airmap.airmapsdk.models.Config
import com.aungkyawpaing.geoshi.model.Polygon
import com.aungkyawpaing.geoshi.model.Position
import com.readystatesoftware.chuck.ChuckInterceptor
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import timber.log.Timber
import java.io.Reader
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val okHttpClientBuilder = OkHttpClient.Builder().addInterceptor(ChuckInterceptor(this))
        AirMap.init(getConfig(this, Moshi.Builder().build()), false, okHttpClientBuilder)
        try {
            inProgress()
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

    private fun inProgress() {
    }

    private fun queue() {
//            client.getAuthorizations().executeAndLogResponse()
//            client.createFlight().executeAndLogResponse()// TODO: Test for Point, Path, and Polygon
//            client.createFlightPoint().executeAndLogResponse()
//            client.createFlightPath().executeAndLogResponse()
//            client.createFlightPolygon().executeAndLogResponse()
//            client.endFlight().executeAndLogResponse()
//            client.deleteFlight().executeAndLogResponse()
//            client.startComm().executeAndLogResponse()
//            client.endComm().executeAndLogResponse()
//            client.createFlightPlan().executeAndLogResponse()
//            client.updateFlightPlan().executeAndLogResponse()
//            client.submitFlightPlan().executeAndLogResponse()
//            client.getFlightBriefing().executeAndLogResponse()
//            client.updatePilot().executeAndLogResponse()
//            client.getAircraft().executeAndLogResponse()
//            client.createAircraft().executeAndLogResponse()
//            client.updateAircraft().executeAndLogResponse()
//            client.deleteAircraft().executeAndLogResponse()
//            client.getRuleset().executeAndLogResponse()
//            client.getRulesets().executeAndLogResponse()
//            client.getRulesets().executeAndLogResponse()
//            client.getRulesets().executeAndLogResponse()
//            client.getEvaluation().executeAndLogResponse()
//            client.getJurisdictions().executeAndLogResponse()
//            client.getJurisdictions().executeAndLogResponse()
//            client.getAdvisories().executeAndLogResponse()
//            client.getAdvisories().executeAndLogResponse()
//            client.getAdvisories().executeAndLogResponse()
//            client.getAdvisories().executeAndLogResponse()
    }

    private fun notWorking() {
        val rulesets = "usa_ama,usa_national_park,usa_sec_91,usa_wilderness_area,usa_sec_336".split(',')
        val geometry = Polygon(
            listOf(
                listOf(
                    Position(-122.1008657255241, 37.44169541637456),
                    Position(-122.06715767436634, 37.44169541637456),
                    Position(-122.06715767436634, 37.40233316192527),
                    Position(-122.1008657255241, 37.40233316192527),
                    Position(-122.1008657255241, 37.44169541637456)
                )
            )
        )
        client.getAdvisories(rulesets, geometry, Date(), Date()).executeAndLogResponse()
        client.getAirspaces(listOf("97f107b9-5688-4842-b98c-18addfcb3a1f", "97f107b9-5688-4842-b98c-18addfcb3a1g")).executeAndLogResponse()
    }

    private fun working() {
        val flightPlanId = "flight_plan|B5Kv2qGB42Z3c3pRb5xwvg9Z4CpnZEK7a8QzRETQQyBzgM8DG3Q"
        client.getFlightPlan(flightPlanId).executeAndLogResponse()
        val flightId = "flight|dwaDYDPGolYY4CQ0kxlGgJN8N7IdRJ8Ypw0LMbyFZn5aMMvJK0Jg"
        client.getFlight(flightId).executeAndLogResponse()
        client.getFlight(flightId, enhance = true).executeAndLogResponse()
        client.getFlightPlanByFlight(flightId).executeAndLogResponse()
        client.getManufacturers().executeAndLogResponse()
        client.getManufacturers("GoPro").executeAndLogResponse()
        client.getModel("c7ed05c7-cbe1-43a4-b2a8-500d5607e994").executeAndLogResponse()
        client.getModels().executeAndLogResponse()
        client.getModels(manufacturerId = "63280fbf-3c7f-47f4-9168-5763899048cd").executeAndLogResponse()
        client.getModels(name = "Karma").executeAndLogResponse()
        client.getFlights(pilotId = "auth0|5761a4279732f5844b1db844").executeAndLogResponse()
        client.getFlights().executeAndLogResponse()
        client.getPilot().executeAndLogResponse()
        client.sendVerificationToken().executeAndLogResponse()
        client.verifySMS(187549).executeAndLogResponse()
        client.getAllAircraft().executeAndLogResponse()
        client.getAirspace("97f107b9-5688-4842-b98c-18addfcb3a1f").executeAndLogResponse()
    }

    private fun <T> genericLogResponseHandler(response: T?, error: Throwable?) {
        response?.let { Timber.v(it.toString()) }
        error?.let { Timber.e(it) }
    }

    private fun <T> Response<T>.executeAndLogResponse() {
        execute(::genericLogResponseHandler)
    }

    private fun getConfig(context: Context, moshi: Moshi) = try {
        moshi.adapter(Config::class.java)
            .fromJson(
                context.resources.assets.open("airmap.config.json")
                    .reader()
                    .use(Reader::readText)
            )!!
    } catch (e: Exception) {
        throw RuntimeException("Please ensure airmap.config.json is in your /assets directory")
    }

}
