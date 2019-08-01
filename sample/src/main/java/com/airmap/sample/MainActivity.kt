package com.airmap.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.airmap.airmapsdk.AirMap
import com.airmap.airmapsdk.models.Manufacturer

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AirMap.init(this)
    }
}
