package com.airmap.sample;

import com.airmap.airmapsdk.AirMap;
import com.airmap.airmapsdk.AirMapClient;
import com.airmap.airmapsdk.ResponseHandler;
import com.airmap.airmapsdk.models.AirMapConfig;
import com.airmap.airmapsdk.models.Aircraft;
import com.airmap.airmapsdk.models.Config;

import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

/**
 * This class is to test/ensure syntax in Java works as expected
 */
public class TestJavaSyntax {
    public static void examples() {
        AirMapConfig config = new AirMapConfig()
        AirMap.init();
        AirMapClient client = AirMap.getClient();
        String userId = AirMap.getUserId();
        client.getAllAircraft(userId).execute((response, throwable) -> {
            response.forEach(System.out::println);
        });
    }
}
