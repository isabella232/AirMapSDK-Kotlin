package com.airmap.sdk.networking

import com.airmap.sdk.utils.iso8601
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type
import java.util.Date

internal class DateConverterFactory : Converter.Factory() {
    override fun stringConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit,
    ): Converter<*, String>? = if (type == Date::class.java) {
        Converter(Date::iso8601)
    } else {
        null
    }
}
