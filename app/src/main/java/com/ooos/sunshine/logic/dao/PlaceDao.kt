package com.ooos.sunshine.logic.dao

import android.content.Context
import androidx.core.content.edit
import com.google.gson.Gson
import com.ooos.sunshine.SunshineApplication
import com.ooos.sunshine.logic.model.PlaceResponse.Place

object PlaceDao {

    fun savePlace(place: Place) {
        sharedPreferences().edit {
            putString("place", Gson().toJson(place))
        }
    }

    fun getPlace(): Place {
        val placeJson = sharedPreferences().getString("place", "")
        return Gson().fromJson(placeJson, Place::class.java)
    }

    fun isSaved() = sharedPreferences().contains("place")

    private fun sharedPreferences() =
        SunshineApplication.context.getSharedPreferences("sunshine", Context.MODE_PRIVATE)
}