package com.dailyweather.android.ui.place

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.dailyweather.android.logic.Repository
import com.dailyweather.android.logic.dao.PlaceDao
import com.dailyweather.android.logic.model.Place

class PlaceViewModel : ViewModel() {
    private val searchLiveData = MutableLiveData<String>()

    val placeLiveData = Transformations.switchMap(searchLiveData) {
        Repository.searchPlaces(it)
    }

    fun searchPlaces(placeName: String){
        searchLiveData.value = placeName
    }

    val places = ArrayList<Place>()

    fun savePlace(place: Place) = Repository.savePlace(place)

    fun getSavedPlace() = Repository.getSavedPlace()

    fun isPlaceSaved() = Repository.isPlaceSaved()
}