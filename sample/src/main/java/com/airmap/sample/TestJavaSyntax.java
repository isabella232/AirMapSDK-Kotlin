package com.airmap.sample;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.airmap.sdk.AirMap;
import com.airmap.sdk.AirMapClient;

/**
 * This class is to test/ensure syntax in Java works as expected
 */
public class TestJavaSyntax extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AirMap.init(MainActivity.getConfig(this));
        AirMapClient client = AirMap.getClient();
        String userId = AirMap.getUserId();
        if (userId != null) {
            // kotlin.Result type is not available in Java since it's an inline class :(
            // see: https://stackoverflow.com/questions/60626607/kotlin-result-type-in-java
            // TODO: Create either helper methods or own implementation of the type
//            client.getAllAircraft(userId).enqueue((aircraft, throwable) -> {
//                if (throwable != null) Timber.e(throwable);
//                if (aircraft != null) aircraft.forEach(System.out::println);
//                aircraft.get(0).getModel().getManufacturer().getId();
//                new Manufacturer("", "", "").getId();
//            });
        }
    }
}
