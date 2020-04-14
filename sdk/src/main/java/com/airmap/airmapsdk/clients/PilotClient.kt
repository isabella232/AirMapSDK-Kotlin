package com.airmap.airmapsdk.clients

import com.airmap.airmapsdk.AirMap
import com.airmap.airmapsdk.Response
import com.airmap.airmapsdk.models.Aircraft
import com.airmap.airmapsdk.models.Pilot
import com.airmap.airmapsdk.models.UpdatePilotRequest
import com.airmap.airmapsdk.models.VerificationRequest
import com.airmap.airmapsdk.models.VerificationResult
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
    fun getPilot(@Path("id") id: String = AirMap.userId.orEmpty()): Response<Pilot>

    @PATCH("{id}")
    @Wrapped(path = ["data"])
    fun updatePilot(
        @Body pilot: UpdatePilotRequest,
        @Path("id") id: String = AirMap.userId.orEmpty()
    ): Response<Pilot>

    @POST("{id}/phone/send_token")
    fun sendVerificationToken(
        @Path("id") pilotId: String = AirMap.userId.orEmpty()
    ): Response<Unit>

    @POST("{id}/phone/verify_token")
    @Wrapped(path = ["data"])
    fun verifySMS(
        @Body verificationRequest: VerificationRequest,
        @Path("id") pilotId: String = AirMap.userId.orEmpty()
    ): Response<VerificationResult>

    @GET("{id}/aircraft")
    @Wrapped(path = ["data"])
    fun getAllAircraft(
        @Path("id") pilotId: String = AirMap.userId.orEmpty()
    ): Response<List<Aircraft>>

    @GET("{id}/aircraft/{aircraft_id}")
    @Wrapped(path = ["data"])
    fun getAircraft(
        @Path("aircraft_id") aircraftId: String,
        @Path("id") pilotId: String = AirMap.userId.orEmpty()
    ): Response<Aircraft>

    @POST("{id}/aircraft")
    @FormUrlEncoded
    @Wrapped(path = ["data"])
    fun createAircraft(
        @Field("nickname") nickname: String,
        @Field("model_id") modelId: String,
        @Field("serial_number") serialNumber: String? = null,
        @Path("id") pilotId: String = AirMap.userId.orEmpty()
    ): Response<Aircraft>

    @PATCH("{id}/aircraft/{aircraft_id}")
    @FormUrlEncoded
    @Wrapped(path = ["data"])
    fun updateAircraftNickname(
        @Path("aircraft_id") aircraftId: String,
        @Field("nickname") nickname: String,
        @Path("id") pilotId: String = AirMap.userId.orEmpty()
    ): Response<Aircraft>

    @DELETE("{id}/aircraft/{aircraft_id}")
    @Wrapped(path = ["data"])
    fun deleteAircraft(
        @Path("aircraft_id") aircraftId: String,
        @Path("id") pilotId: String = AirMap.userId.orEmpty()
    ): Response<Unit>
}
