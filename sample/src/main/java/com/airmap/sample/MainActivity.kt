package com.airmap.sample

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.airmap.airmapsdk.AirMap
import com.airmap.airmapsdk.models.Config
import com.squareup.moshi.Moshi
import timber.log.Timber
import java.io.Reader

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AirMap.init(getConfig(this, Moshi.Builder().build()))
        demoAircraft()
        demoPilot()
    }

    private fun <T> genericLogResponseHandler(response: T?, error: Throwable?) {
        response?.let { Timber.v(it.toString()) }
        error?.let { Timber.e(it) }
    }

    private fun demoAircraft() {
        AirMap.client.getManufacturers().execute(::genericLogResponseHandler)
        AirMap.client.getManufacturers("GoPro").execute(::genericLogResponseHandler)
        AirMap.client.getModels().execute(::genericLogResponseHandler)
        AirMap.client.getModels(manufacturerId = "63280fbf-3c7f-47f4-9168-5763899048cd").execute(::genericLogResponseHandler)
        AirMap.client.getModels(name = "Karma").execute(::genericLogResponseHandler)
        AirMap.client.getModel("c7ed05c7-cbe1-43a4-b2a8-500d5607e994").execute(::genericLogResponseHandler)
    }

    private fun demoPilot() {
        AirMap.client.getPilot().execute(::genericLogResponseHandler)
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
