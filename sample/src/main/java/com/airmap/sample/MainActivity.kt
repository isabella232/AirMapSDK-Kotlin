package com.airmap.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.airmap.airmapsdk.AirMap
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AirMap.init(this)
        demoAircraft()
    }

    private fun <T> genericLogResponseHandler(response: T?, error: Throwable?) {
        response?.let { Timber.v(it.toString()) }
        error?.let { Timber.e(it) }
    }

    private fun demoAircraft() {
        AirMap.client.getManufacturers().execute(::genericLogResponseHandler)
        AirMap.client.getManufacturers().execute(::genericLogResponseHandler)
        AirMap.client.getManufacturers("GoPro").execute(::genericLogResponseHandler)
        AirMap.client.getModels().execute(::genericLogResponseHandler)
        AirMap.client.getModels(manufacturerId = "63280fbf-3c7f-47f4-9168-5763899048cd").execute(::genericLogResponseHandler)
        AirMap.client.getModels(name = "Karma").execute(::genericLogResponseHandler)
        AirMap.client.getModel("c7ed05c7-cbe1-43a4-b2a8-500d5607e994").execute(::genericLogResponseHandler)
    }
}
