package com.vosaa.majoritytechassignment.data.datastore

import androidx.datastore.core.Serializer
import com.vosaa.majoritytechassignment.domain.models.ResponseCountry
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

@Suppress("BlockingMethodInNonBlockingContext")
object CountrySerializer : Serializer<ResponseCountry> {
    override val defaultValue: ResponseCountry
        get() = ResponseCountry()

    override suspend fun readFrom(input: InputStream): ResponseCountry {
        return try {
            Json.decodeFromString(
                deserializer = ResponseCountry.serializer(),
                string = input.readBytes().decodeToString()
            )
        } catch (e: SerializationException) {
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: ResponseCountry, output: OutputStream) {
        output.write(
            Json.encodeToString(
                serializer = ResponseCountry.serializer(), value = t
            ).encodeToByteArray()
        )
    }
}