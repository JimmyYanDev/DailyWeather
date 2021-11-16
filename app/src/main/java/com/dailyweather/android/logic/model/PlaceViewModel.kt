package com.dailyweather.android.logic.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.dailyweather.android.logic.Repository

class PlaceViewModel : ViewModel() {
    private val searchLiveData = MutableLiveData<String>()

    val placeLiveData = Transformations.switchMap(searchLiveData) {
        Repository.searchPlaces(it)
    }

    fun searchPlaces(placeName: String){
        searchLiveData.value = placeName
    }

    val places = ArrayList<Place>()
}