package com.ooos.sunshine.logic

import androidx.lifecycle.liveData
import com.ooos.sunshine.logic.model.PlaceResponse.Place
import com.ooos.sunshine.logic.network.SunshineNetwork
import kotlinx.coroutines.Dispatchers
import java.lang.RuntimeException

object Repository {

    fun searchPlaces(query: String) = liveData(Dispatchers.IO) {
        val result = try {
            val placeResponse = SunshineNetwork.searchPlaces(query)
            if (placeResponse.status == "ok") {
                val places = placeResponse.places
                Result.success(places)
            } else {
                Result.failure(RuntimeException("placeResponse status is ${placeResponse.places}"))
            }
        } catch (e: Exception) {
            Result.failure<List<Place>>(e)
        }
        emit(result)
    }
}