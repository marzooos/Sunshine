package com.ooos.sunshine.ui.weather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.ooos.sunshine.logic.Repository
import com.ooos.sunshine.logic.model.PlaceResponse.Location

class WeatherViewModel : ViewModel() {

    private val _locationLiveData = MutableLiveData<Location>()

    var lng = ""
    var lat = ""
    var placeName = ""

    val weatherLiveData = Transformations.switchMap(_locationLiveData) { location ->
        Repository.queryWeather(location.lng, location.lat)
    }

    fun queryWeather(lng: String, lat: String) {
        _locationLiveData.value = Location(lat, lng)
    }
}