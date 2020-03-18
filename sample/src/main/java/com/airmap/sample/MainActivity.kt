package com.airmap.sample

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.airmap.airmapsdk.AirMap
import com.airmap.airmapsdk.AirMap.client
import com.airmap.airmapsdk.Response
import com.airmap.airmapsdk.models.Config
import com.airmap.airmapsdk.models.FlightPlan
import com.aungkyawpaing.geoshi.model.Polygon
import com.aungkyawpaing.geoshi.model.Position
import com.readystatesoftware.chuck.ChuckInterceptor
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import timber.log.Timber
import java.io.Reader
import java.util.Date

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Timber.plant(Timber.DebugTree())
        val okHttpClientBuilder = OkHttpClient.Builder().addInterceptor(ChuckInterceptor(this))
        AirMap.init(getConfig(this, Moshi.Builder().build()), false, okHttpClientBuilder)
        try {
            examples()
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

    private fun examples() {
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
        val flightPlanIds = listOf(
            "flight_plan|W3L7xWw2Z4qEacmd5pw72qXX42uzXddPKRleZyXtM0BkMpgPpxbA",
            "flight_plan|B5Kv2qGB42Z3c3pRb5xwvg9Z4CpnZEK7a8QzRETQQyBzgM8DG3Q"
        )
        val rulesets = "usa_ama,usa_national_park,usa_sec_91,usa_wilderness_area,usa_sec_336"
            .split(',')
        val flightPlanId = "flight_plan|B5Kv2qGB42Z3c3pRb5xwvg9Z4CpnZEK7a8QzRETQQyBzgM8DG3Q"
        val flightId = "flight|dwaDYDPGolYY4CQ0kxlGgJN8N7IdRJ8Ypw0LMbyFZn5aMMvJK0Jg"
        val modelId = "c7ed05c7-cbe1-43a4-b2a8-500d5607e994"
        val manufacturerId = "63280fbf-3c7f-47f4-9168-5763899048cd"
        val pilotId = "auth0|5761a4279732f5844b1db844"
        val airspaceId1 = "97f107b9-5688-4842-b98c-18addfcb3a1f"
        val airspaceId2 = "8b819ff6-c9f9-4f70-a747-5263790a640b"
        val aircraftId = "aircraft|Ew6wlKai03Og6ZHqp5loNiBkK0zX"
        val mavicProModelId = "18546c9b-c032-4bfd-9741-4cd24e368618"
        val updateNickname = "Update ${System.currentTimeMillis()}"
        val newNickname = "New ${System.currentTimeMillis()}"
        client.getFlightPlan(flightPlanId).executeAndLogResponse()
        client.getFlight(flightId).executeAndLogResponse()
        client.getFlight(flightId, enhance = true).executeAndLogResponse()
        client.getFlightPlanByFlight(flightId).executeAndLogResponse()
        client.getManufacturers().executeAndLogResponse()
        client.getManufacturers("GoPro").executeAndLogResponse()
        client.getModel(modelId).executeAndLogResponse()
        client.getModels().executeAndLogResponse()
        client.getModels(manufacturerId).executeAndLogResponse()
        client.getModels(name = "Karma").executeAndLogResponse()
        client.getFlights(pilotId).executeAndLogResponse()
        client.getFlights().executeAndLogResponse()
        client.sendVerificationToken().executeAndLogResponse()
        client.verifySMS(152986).executeAndLogResponse()
        client.getPilot().executeAndLogResponse()
        client.getAllAircraft().executeAndLogResponse()
        client.getAirspace(airspaceId1).executeAndLogResponse()
        client.getAirspaces(listOf(airspaceId1, airspaceId2)).executeAndLogResponse()
        client.getAdvisories(rulesets, geometry, Date(), Date()).executeAndLogResponse()
        client.getAircraft(aircraftId).executeAndLogResponse()
        client.updateAircraftNickname(aircraftId, updateNickname).executeAndLogResponse()
        // TODO: Look into how we can remove the need for ".execute"
        client.createAircraft(newNickname, mavicProModelId).execute { aircraft, error ->
            genericLogResponseHandler(aircraft, error)
            aircraft?.id?.let { client.deleteAircraft(it).executeAndLogResponse() }
        }
        // Fight creation process
        client.getRulesets(geometry).executeAndLogResponse()
        client.createFlightPlan(
            FlightPlan(
                id = null,
                pilotId = AirMap.userId.orEmpty(),
                flightId = null,
                aircraftId = "aircraft|PngXy95H9OYWWpCnJ2wdbTO8kM33",
                startTime = Date(),
                endTime = Date(System.currentTimeMillis() + 1000000),
                createdAt = null,
                creationDate = null,
                buffer = 762.0,
                takeoffLatitude = 37.42,
                takeoffLongitude = -122.084,
                geometry = geometry,
                minAltitudeAgl = 10.0,
                maxAltitudeAgl = 40.0,
                rulesetIds = rulesets,
                flightDescription = "${System.currentTimeMillis()}",
                flightFeatures = mutableMapOf(
                    "test" to "test"
                ),
                isPublic = false,
                shouldNotify = false
            )
        ).execute { flightPlan, error ->
            genericLogResponseHandler(flightPlan, error)
            val fpId = flightPlan!!.id!!
            client.getFlightPlanBriefing(fpId).executeAndLogResponse()
            client.updateFlightPlan(
                fpId, flightPlan.copy(
                    maxAltitudeAgl = 100.0,
                    flightFeatures = mapOf("test2" to true)
                )
            ).execute { updatedFlightPlan, updatedError ->
                genericLogResponseHandler(updatedFlightPlan, updatedError)
                client.getFlightPlan(fpId).executeAndLogResponse()
                client.submitFlightPlan(fpId).execute { submittedFlightPlan, submitError ->
                    genericLogResponseHandler(submittedFlightPlan, submitError)
                    val fId = submittedFlightPlan!!.flightId!!
                    client.getFlight(fId).executeAndLogResponse()
                    client.getFlightPlanByFlight(fId).executeAndLogResponse()
                    client.startComm(fId).execute { comm, commError ->
                        genericLogResponseHandler(comm, commError)
                        client.endComm(fId).execute { unit, endCommError ->
                            genericLogResponseHandler(unit, endCommError)
                            client.endFlight(fId).execute { endedFlight, endFlightError ->
                                genericLogResponseHandler(endedFlight, endFlightError)
                                client.deleteFlight(fId).executeAndLogResponse()
                            }
                        }
                    }
                }
            }

            client.getWeather(
                flightPlan.takeoffLatitude!!,
                flightPlan.takeoffLongitude!!,
                "2020-03-15T03:54:12.021Z",
                "2020-03-15T04:10:52.021Z"
            ).executeAndLogResponse()
        }

        client.getAuthorizations(flightPlanIds).executeAndLogResponse()
        client.getRuleset(rulesets[0]).executeAndLogResponse()
        client.getRulesets(rulesets).executeAndLogResponse()
        client.getRulesets(geometry).executeAndLogResponse()
        client.getEvaluation(geometry, rulesets).executeAndLogResponse()
        // This should be working, but when testing there were internal errors, so revisit
        // (update was succeeding but not returning the full Pilot object)
        client.getPilot().execute { pilot, error ->
            genericLogResponseHandler(pilot, error)
            val newLastName = "Update ${System.currentTimeMillis()}"
            pilot?.let { client.updatePilot(lastName = newLastName).executeAndLogResponse() }
        }
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
