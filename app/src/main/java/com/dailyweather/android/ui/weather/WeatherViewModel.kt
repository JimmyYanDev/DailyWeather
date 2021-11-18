package com.dailyweather.android.ui.weather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.dailyweather.android.logic.Repository
import com.dailyweather.android.logic.model.Location

class WeatherViewModel : ViewModel() {
    private var locationLiveData = MutableLiveData<Location>()

    var placeName = ""
    var locationLng = ""
    var locationLat = ""

    var weatherLiveData = Transformations.switchMap(locationLiveData) {
        Repository.refreshWeather(it.lng, it.lat)
    }

    fun refreshWeather(lng: String, lat: String){
        locationLiveData.value = Location(lng, lat)
    }
}