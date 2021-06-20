package com.ooos.sunshine.logic.model

import com.google.gson.annotations.SerializedName

/**
 * JSON data of geographic location.
 * doc : https://api.caiyunapp.com/v2/place?query=北京&token={token}&lang=zh_CN
 */
data class PlaceResponse(val status: String, val places: List<Place>) {

    data class Place(
        val name: String,
        val location: Location,
        @SerializedName("formatted_address") val address: String
    )

    data class Location(val lat: String, val lng: String)
}