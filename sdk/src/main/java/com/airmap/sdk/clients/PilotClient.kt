package com.airmap.sdk.clients

import com.airmap.sdk.AirMap
import com.airmap.sdk.models.Aircraft
import com.airmap.sdk.models.Pilot
import com.airmap.sdk.models.UpdatePilotRequest
import com.airmap.sdk.models.VerificationRequest
import com.airmap.sdk.models.VerificationResult
import com.airmap.sdk.networking.AirMapCall
import com.serjltt.moshi.adapters.Wrapped
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface PilotClient {
    @GET("{id}")
    @Wrapped(path = ["data"])
    fun getPilot(
        @Path("id") id: String = AirMap.userId.orEmpty(),
    ): AirMapCall<Pilot>

    @PATCH("{id}")
    @Wrapped(path = ["data"])
    fun updatePilot(
        @Body pilot: UpdatePilotRequest,
        @Path("id") id: String = AirMap.userId.orEmpty(),
    ): AirMapCall<Pilot>

    @POST("{id}/phone/send_token")
    fun sendVerificationToken(
        @Path("id") pilotId: String = AirMap.userId.orEmpty(),
    ): AirMapCall<Unit>

    @POST("{id}/phone/verify_token")
    @Wrapped(path = ["data"])
    fun verifySMS(
        @Body verificationRequest: VerificationRequest,
        @Path("id") pilotId: String = AirMap.userId.orEmpty(),
    ): AirMapCall<VerificationResult>

    @GET("{id}/aircraft")
    @Wrapped(path = ["data"])
    fun getAllAircraft(
        @Path("id") pilotId: String = AirMap.userId.orEmpty(),
    ): AirMapCall<List<Aircraft>>

    @GET("{id}/aircraft/{aircraft_id}")
    @Wrapped(path = ["data"])
    fun getAircraft(
        @Path("aircraft_id") aircraftId: String,
        @Path("id") pilotId: String = AirMap.userId.orEmpty(),
    ): AirMapCall<Aircraft>

    @POST("{id}/aircraft")
    @FormUrlEncoded
    @Wrapped(path = ["data"])
    fun createAircraft(
        @Field("nickname") nickname: String,
        @Field("model_id") modelId: String,
        @Field("serial_number") serialNumber: String? = null,
        @Path("id") pilotId: String = AirMap.userId.orEmpty(),
    ): AirMapCall<Aircraft>

    @PATCH("{id}/aircraft/{aircraft_id}")
    @FormUrlEncoded
    @Wrapped(path = ["data"])
    fun updateAircraftNickname(
        @Path("aircraft_id") aircraftId: String,
        @Field("nickname") nickname: String,
        @Path("id") pilotId: String = AirMap.userId.orEmpty(),
    ): AirMapCall<Aircraft>

    @DELETE("{id}/aircraft/{aircraft_id}")
    @Wrapped(path = ["data"])
    fun deleteAircraft(
        @Path("aircraft_id") aircraftId: String,
        @Path("id") pilotId: String = AirMap.userId.orEmpty(),
    ): AirMapCall<Unit>
}
