package com.ooos.sunshine.logic

import androidx.lifecycle.liveData
import com.ooos.sunshine.logic.dao.PlaceDao
import com.ooos.sunshine.logic.model.PlaceResponse.Place
import com.ooos.sunshine.logic.model.Weather
import com.ooos.sunshine.logic.network.SunshineNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.lang.RuntimeException
import kotlin.coroutines.CoroutineContext

object Repository {

    fun searchPlaces(query: String) = fire(Dispatchers.IO) {
        val placeResponse = SunshineNetwork.searchPlaces(query)
        if (placeResponse.status == "ok") {
            val places = placeResponse.places
            Result.success(places)
        } else {
            Result.failure(RuntimeException("PlaceResponse status is ${placeResponse.status}"))
        }
    }

    fun queryWeather(lng: String, lat: String) = fire(Dispatchers.IO) {
        coroutineScope {
            val deferredRealtime = async {
                SunshineNetwork.getRealtimeWeather(lng, lat)
            }
            val deferredDaily = async {
                SunshineNetwork.getDailyWeather(lng, lat)
            }
            val realtimeResponse = deferredRealtime.await()
            val dailyResponse = deferredDaily.await()

            if (realtimeResponse.status == "ok" && dailyResponse.status == "ok") {
                val weather =
                    Weather(realtimeResponse.result.realtime, dailyResponse.result.daily)
                Result.success(weather)
            } else {
                Result.failure(RuntimeException("Realtime response status is ${realtimeResponse.status}, Daily response status is ${dailyResponse.status}."))
            }
        }
    }

    fun savePlace(place: Place) = PlaceDao.savePlace(place)

    fun getSavedPlace() = PlaceDao.getPlace()

    fun isPlaceSaved() = PlaceDao.isSaved()

    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
        liveData(context) {
            val result = try {
                block()
            } catch (e: Exception) {
                Result.failure(e)
            }
            emit(result)
        }
}