package com.airmap.sdk.networking

import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

internal class CommaSeparatedConverterFactory : Converter.Factory() {
    override fun stringConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit,
    ): Converter<*, String>? = if (
        type is ParameterizedType
        && Iterable::class.java.isAssignableFrom(type as Class<*>)
        && annotations.any { it is CommaSeparated }
    ) {
        Converter { value: Iterable<*>? -> value?.joinToString(",") }
    } else {
        null
    }
}
