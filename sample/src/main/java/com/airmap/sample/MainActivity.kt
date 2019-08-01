package com.airmap.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.airmap.airmapsdk.AirMap
import com.airmap.airmapsdk.ResponseHandler
import com.airmap.airmapsdk.models.Manufacturer
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AirMap.init(this)
        demoAircraft()
    }

    private fun <T> genericLogResponseHandler(r: T?, t: Throwable?) {
        Timber.v(r?.toString()); Timber.e(t)
    }

    private fun demoAircraft() {
        AirMap.aircraftClient.getManufacturers().execute(::genericLogResponseHandler)
        AirMap.aircraftClient.getManufacturers().execute(::genericLogResponseHandler)
        AirMap.aircraftClient.getManufacturers("GoPro").execute(::genericLogResponseHandler)
        AirMap.aircraftClient.getModels().execute(::genericLogResponseHandler)
        AirMap.aircraftClient.getModels(manufacturerId = "63280fbf-3c7f-47f4-9168-5763899048cd")
            .execute(::genericLogResponseHandler)
        AirMap.aircraftClient.getModels(name = "Karma").execute(::genericLogResponseHandler)
        AirMap.aircraftClient.getModel("c7ed05c7-cbe1-43a4-b2a8-500d5607e994").execute(::genericLogResponseHandler)
    }
}
