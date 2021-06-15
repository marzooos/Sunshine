package com.ooos.sunshine.ui.place

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.ooos.sunshine.logic.Repository
import com.ooos.sunshine.logic.model.PlaceResponse.Place

class PlaceViewModel : ViewModel() {

    private val _searchLiveData = MutableLiveData<String>()

    // 缓存数据, 避免界面重建导致数据丢失.
    val placeList = ArrayList<Place>()

    val placeLiveData = Transformations.switchMap(_searchLiveData) { query ->
        Repository.searchPlaces(query)
    }

    fun searchPlaces(query: String) {
        _searchLiveData.value = query
    }
}