package com.ooos.sunshine.logic.model

import com.google.gson.annotations.SerializedName

/**
 * JSON data of realtime weather.
 * doc : https://api.caiyunapp.com/v2.5/{token}/116.4073963,39.9041999/realtime.json
 */
data class RealtimeResponse(val status: String, val result: Result) {

    data class Result(val realtime: Realtime)

    data class Realtime(
        val temperature: Float,
        val skycon: String,
        @SerializedName("air_quality") val airQuality: AirQuality
    )

    data class AirQuality(val aqi: AQI)

    data class AQI(val chn: Float)
}