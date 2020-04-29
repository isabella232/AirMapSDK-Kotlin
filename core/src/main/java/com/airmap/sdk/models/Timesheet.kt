package com.airmap.sdk.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TimeSheet(
    @Json(name = "active") val active: Boolean?, // null when active state is unknown
    @Json(name = "data") val data: Data?,
) {
    @JsonClass(generateAdapter = true)
    data class Data(
        @Json(name = "utc_offset") val utcOffset: Hours?,
        @Json(name = "excluded") val excluded: Boolean?,
        @Json(name = "daylight_saving_adjust") val daylightSavingAdjust: Boolean?,
        @Json(name = "day") val day: Day?,
        @Json(name = "day_til") val dayTil: Day?,
        @Json(name = "start") val start: Marker?,
        @Json(name = "end") val end: Marker?,
    ) {
        /**
         * This represents a *relative* marker in time
         */
        @JsonClass(generateAdapter = true)
        data class Marker(
            @Json(name = "event") val eventDescriptor: Event?,
            @Json(name = "event_interpretation") val eventInterpretation: Event.Interpretation?,
            @Json(name = "event_offset") val eventOffset: Int?,
            @Json(name = "time") val time: Time?,
            @Json(name = "date") val date: Date?,
        ) {
            /**
             * This represents a *relative* marker of time within a day
             */
            @JsonClass(generateAdapter = true)
            data class Time(
                val hour: Int,
                val minute: Int,
            )

            /**
             * This represents a *relative* marker of date within a year (not to be confused with
             * [java.util.Date])
             */
            @JsonClass(generateAdapter = true)
            data class Date(
                val month: Int,
                val day: Int,
            )
        }
    }
}

/**
 * Contains the localized [name] for [id]
 */
@JsonClass(generateAdapter = true)
data class Day(
    @Json(name = "name") val name: String,
    @Json(name = "id") val id: Id,
) {
    enum class Id {
        @Json(name = "day_monday") Monday,
        @Json(name = "day_tuesday") Tuesday,
        @Json(name = "day_wednesday") Wednesday,
        @Json(name = "day_thursday") Thursday,
        @Json(name = "day_friday") Friday,
        @Json(name = "day_saturday") Saturday,
        @Json(name = "day_sunday") Sunday,
        @Json(name = "day_work_day") WorkDay,
        @Json(name = "day_before_work_day") BeforeWorkDay,
        @Json(name = "day_after_work_day") AfterWorkDay,
        @Json(name = "day_holiday") Holiday,
        @Json(name = "day_before_holiday") BeforeHoliday,
        @Json(name = "day_after_holiday") AfterHoliday,
        @Json(name = "day_busy_friday") BusyFriday,
        @Json(name = "day_any") Any,
    }
}

/**
 * Contains the localized [name] for [id]
 */
@JsonClass(generateAdapter = true)
data class Event(
    @Json(name = "name") val name: String,
    @Json(name = "id") val id: Id,
) {
    enum class Id {
        @Json(name = "event_sunrise") Sunrise,
        @Json(name = "event_sunset") Sunset,
    }

    enum class Interpretation {
        @Json(name = "event_interpretation_earliest") Earliest,
        @Json(name = "event_interpretation_latest") Latest,
    }
}


