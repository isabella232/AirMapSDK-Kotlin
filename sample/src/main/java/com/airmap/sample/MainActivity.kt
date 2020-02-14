package com.airmap.sample

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.airmap.airmapsdk.AirMap
import com.airmap.airmapsdk.AirMap.client
import com.airmap.airmapsdk.Response
import com.airmap.airmapsdk.models.Config
import com.readystatesoftware.chuck.ChuckInterceptor
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import timber.log.Timber
import java.io.Reader

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val okHttpClientBuilder = OkHttpClient.Builder().addInterceptor(ChuckInterceptor(this))
        AirMap.init(getConfig(this, Moshi.Builder().build()), okHttpClientBuilder = okHttpClientBuilder)
        demoAll()
    }

    private fun <T> genericLogResponseHandler(response: T?, error: Throwable?) {
        response?.let { Timber.v(it.toString()) }
        error?.let { Timber.e(it) }
    }
    
    private fun <T> Response<T>.executeAndLogResponse() {
        execute(::genericLogResponseHandler)
    }

    private fun demoAll() {
        try {
//            client.getManufacturers().executeAndLogResponse()
//            client.getManufacturers().executeAndLogResponse()
//            client.getManufacturers("GoPro").executeAndLogResponse()
//            client.getModel("c7ed05c7-cbe1-43a4-b2a8-500d5607e994").executeAndLogResponse()
//            client.getModels().executeAndLogResponse()
//            client.getModels(manufacturerId = "63280fbf-3c7f-47f4-9168-5763899048cd").executeAndLogResponse()
//            client.getModels(name = "Karma").executeAndLogResponse()
            client.getFlights(pilotId = "auth0|5761a4279732f5844b1db844").executeAndLogResponse()
//            client.getFlights().executeAndLogResponse()
//            client.getPilot().executeAndLogResponse()
//            client.sendVerificationToken().executeAndLogResponse()
//            client.getAllAircraft().executeAndLogResponse()
        } catch (e: Exception) {
            Timber.e(e)
        }


//        AirMap.client.getAdvisories().executeAndLogResponse()
//        AirMap.client.getAirspace().executeAndLogResponse()
//        AirMap.client.getAirspaces().executeAndLogResponse()
//        AirMap.client.getFlight().executeAndLogResponse()
//        AirMap.client.createFlight() .executeAndLogResponse()// TODO: Test for Point, Path, and Polygon
//        AirMap.client.createFlightPoint().executeAndLogResponse()
//        AirMap.client.createFlightPath().executeAndLogResponse()
//        AirMap.client.createFlightPolygon().executeAndLogResponse()
//        AirMap.client.endFlight().executeAndLogResponse()
//        AirMap.client.deleteFlight().executeAndLogResponse()
//        AirMap.client.startComm().executeAndLogResponse()
//        AirMap.client.endComm().executeAndLogResponse()
//        AirMap.client.getFlightPlan().executeAndLogResponse()
//        AirMap.client.createFlightPlan().executeAndLogResponse()
//        AirMap.client.updateFlightPlan().executeAndLogResponse()
//        AirMap.client.submitFlightPlan().executeAndLogResponse()
//        AirMap.client.getFlightBriefing().executeAndLogResponse()
//        AirMap.client.updatePilot().executeAndLogResponse()
//        AirMap.client.verifySMS().executeAndLogResponse()
//        AirMap.client.verifySMS().executeAndLogResponse()
//        AirMap.client.getAircraft().executeAndLogResponse()
//        AirMap.client.createAircraft().executeAndLogResponse()
//        AirMap.client.updateAircraft().executeAndLogResponse()
//        AirMap.client.deleteAircraft().executeAndLogResponse()
//        AirMap.client.getRuleset().executeAndLogResponse()
//        AirMap.client.getRulesets().executeAndLogResponse()
//        AirMap.client.getRulesets().executeAndLogResponse()
//        AirMap.client.getRulesets().executeAndLogResponse()
//        AirMap.client.getEvaluation().executeAndLogResponse()
//        AirMap.client.getJurisdictions().executeAndLogResponse()
//        AirMap.client.getJurisdictions().executeAndLogResponse()
//        AirMap.client.getAdvisories().executeAndLogResponse()
//        AirMap.client.getAdvisories().executeAndLogResponse()
//        AirMap.client.getAdvisories().executeAndLogResponse()
//        AirMap.client.getAdvisories().executeAndLogResponse()
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
