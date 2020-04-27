package com.airmap.sdk.clients

import com.airmap.sdk.models.Airspace
import com.airmap.sdk.networking.AirMapCall
import com.airmap.sdk.networking.CommaSeparated
import com.aungkyawpaing.geoshi.model.Geometry
import com.serjltt.moshi.adapters.Wrapped
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.Date

interface AirspaceClient {
    /**
     * Get an [Airspace] by [id]
     */
    @GET("{id}")
    @Wrapped(path = ["data"])
    fun getAirspace(
        @Path("id") id: String,
    ): AirMapCall<Airspace>

    /**
     * Get [Airspace]s specified by [ids]
     */
    @GET("list")
    @Wrapped(path = ["data"])
    fun getAirspaces(
        @CommaSeparated @Query("ids", encoded = true) ids: List<String>,
    ): AirMapCall<List<Airspace>>

    /**
     * Search for Airspace objects by the following parameters
     *
     * @param types [Airspace.Type]s to include in response
     * @param ignoredTypes [Airspace.Type]s to ignore in response
     * @param full Return full [Airspace] if true
     * @param geometry Search for airspace intersection with [geometry]
     * @param buffer Distance to buffer geometry parameter in meters
     * @param limit Max number of airspace objects to return (max=100)
     * @param offset Offset of airspace objects to return
     * @param datetime Search for airspace that was active at this time in the past (not available
     * before August 2016)
     */
     @GET("search")
     @Wrapped(path = ["data"])
     fun searchAirspace(
        @Query("types") @CommaSeparated types: List<Airspace.Type>?,
        @Query("ignored_types") @CommaSeparated ignoredTypes: List<Airspace.Type>?,
        @Query("full") full: Boolean?,
        @Query("geometry") geometry: Geometry?,
        @Query("buffer") buffer: Double?,
        @Query("limit") limit: Int?,
        @Query("offset") offset: Int?,
        @Query("datetime") datetime: Date?,
     ): AirMapCall<List<Airspace>>
}
