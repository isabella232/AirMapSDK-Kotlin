package com.airmap.airmapsdk.clients

import com.airmap.airmapsdk.AirMap
import com.airmap.airmapsdk.Response
import com.airmap.airmapsdk.models.Aircraft
import com.airmap.airmapsdk.models.Pilot
import com.airmap.airmapsdk.models.VerificationRequest
import com.airmap.airmapsdk.models.VerificationResult
import com.serjltt.moshi.adapters.Wrapped
import retrofit2.http.*

interface PilotClient {
    @GET("{id}")
    @Wrapped(path = ["data"])
    fun getPilot(@Path("id") pilotId: String = AirMap.userId.orEmpty()): Response<Pilot>

    @PATCH("{id}")
    @Wrapped(path = ["data"])
    fun updatePilot(
        @Body updatedPilot: Pilot,
        @Path("id") pilotId: String = AirMap.userId.orEmpty()
    ): Response<Pilot>

    @POST("{id}/phone/send_token")
    fun sendVerificationToken(@Path("id") pilotId: String = AirMap.userId.orEmpty()): Response<Unit>

    @POST("{id}/phone/verify_token")
    @Headers("Content-Type: application/json")
    @Wrapped(path = ["data"])
    fun verifySMS(
        @Body body: VerificationRequest,
        @Path("id") pilotId: String = AirMap.userId.orEmpty()
    ): Response<VerificationResult>

    @GET("{id}/aircraft")
    @Wrapped(path = ["data"])
    fun getAllAircraft(@Path("id") pilotId: String = AirMap.userId.orEmpty()): Response<List<Aircraft>>

    @GET("{id}/aircraft/{aircraftId}")
    @Wrapped(path = ["data"])
    fun getAircraft(
        @Path("aircraftId") aircraftId: String,
        @Path("id") pilotId: String = AirMap.userId.orEmpty()
    ): Response<Aircraft>

    // TODO: Verify if these parameters need to be posted as a JSON body instead of Form
    // TODO: If so, Create some kind of POST body adapter
    @POST("{id}/aircraft")
    @FormUrlEncoded
    @Wrapped(path = ["data"])
    fun createAircraft(
        @Field("nickname") nickname: String,
        @Field("modelId") modelId: String,
        @Path("id") pilotId: String = AirMap.userId.orEmpty()
    ): Response<Aircraft>

    @PATCH("{id}/aircraft/{aircraftId}")
    @FormUrlEncoded
    @Wrapped(path = ["data"])
    fun updateAircraft(
        @Field("nickname") nickname: String,
        @Path("aircraftId") aircraftId: String,
        @Path("id") pilotId: String = AirMap.userId.orEmpty()
    ): Response<Aircraft>

    @DELETE("{id}/aircraft/{aircraftId}")
    @Wrapped(path = ["data"])
    fun deleteAircraft(
        @Path("aircraftId") aircraftId: String,
        @Path("id") pilotId: String = AirMap.userId.orEmpty()
    ): Response<Unit>
}
