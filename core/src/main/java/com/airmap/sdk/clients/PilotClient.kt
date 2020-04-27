package com.airmap.sdk.clients

import com.airmap.sdk.AirMap
import com.airmap.sdk.models.Aircraft
import com.airmap.sdk.models.Pilot
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
    @GET("profile")
    @Wrapped(path = ["data"])
    fun getAuthenticatedPilot(): AirMapCall<Pilot>

    /**
     * Get [Pilot] identified by [id]
     */
    @GET("{id}")
    @Wrapped(path = ["data"])
    fun getPilot(
        @Path("id") id: String = AirMap.userId.orEmpty(),
    ): AirMapCall<Pilot>

    /**
     * Update a pilot's profile. All fields are optional. Only the fields being updated will be
     * returned in the response. To get the full [Pilot], call [getPilot]. [Pilot.appMetadata] can
     * be used to store miscellaneous metadata for your specific application. [Pilot.userMetadata]
     * can be used to store miscellaneous metadata about the user. [Pilot.phone] should be verified
     * with a subsequent call to [sendVerificationToken] and [verifySMS]
     */
    @PATCH("{id}")
    @Wrapped(path = ["data"])
    fun updatePilot(
        @Body pilot: Pilot,
        @Path("id") id: String = AirMap.userId.orEmpty(),
    ): AirMapCall<Pilot>

    /**
     * Send an SMS containing a verification token to the phone number the user has on file. This
     * token should be confirmed by a subsequent call to [verifySMS]
     */
    @POST("{id}/phone/send_token")
    fun sendVerificationToken(
        @Path("id") pilotId: String = AirMap.userId.orEmpty(),
    ): AirMapCall<Unit>

    /**
     * Verify the logged in user's account with the [VerificationRequest.token] they received via
     * SMS from a preceding call to [sendVerificationToken]
     */
    @POST("{id}/phone/verify_token")
    @Wrapped(path = ["data"])
    fun verifySMS(
        @Body verificationRequest: VerificationRequest,
        @Path("id") pilotId: String = AirMap.userId.orEmpty(),
    ): AirMapCall<VerificationResult>

    /**
     * Get all the user's aircraft
     */
    @GET("{id}/aircraft")
    @Wrapped(path = ["data"])
    fun getAllAircraft(
        @Path("id") pilotId: String = AirMap.userId.orEmpty(),
    ): AirMapCall<List<Aircraft>>

    /**
     * Get details about a user's aircraft identified by [aircraftId]
     *
     * @param aircraftId
     * @param pilotId
     * @return
     */
    @GET("{id}/aircraft/{aircraft_id}")
    @Wrapped(path = ["data"])
    fun getAircraft(
        @Path("aircraft_id") aircraftId: String,
        @Path("id") pilotId: String = AirMap.userId.orEmpty(),
    ): AirMapCall<Aircraft>

    /**
     * Create a new aircraft for the user, with a [nickname] and the model of the aircraft
     * (identified by [modelId]). Optionally, the [serialNumber] of the aircraft can also be
     * specified
     */
    @POST("{id}/aircraft")
    @FormUrlEncoded
    @Wrapped(path = ["data"])
    fun createAircraft(
        @Field("nickname") nickname: String,
        @Field("model_id") modelId: String,
        @Field("serial_number") serialNumber: String? = null,
        @Path("id") pilotId: String = AirMap.userId.orEmpty(),
    ): AirMapCall<Aircraft>

    /**
     * Set an updated [nickname] for the aircraft identified by [aircraftId]
     */
    @PATCH("{id}/aircraft/{aircraft_id}")
    @FormUrlEncoded
    @Wrapped(path = ["data"])
    fun updateAircraftNickname(
        @Path("aircraft_id") aircraftId: String,
        @Field("nickname") nickname: String,
        @Path("id") pilotId: String = AirMap.userId.orEmpty(),
    ): AirMapCall<Aircraft>

    /**
     * Delete user's aircraft identified by [aircraftId]
     */
    @DELETE("{id}/aircraft/{aircraft_id}")
    @Wrapped(path = ["data"])
    fun deleteAircraft(
        @Path("aircraft_id") aircraftId: String,
        @Path("id") pilotId: String = AirMap.userId.orEmpty(),
    ): AirMapCall<Unit>
}
