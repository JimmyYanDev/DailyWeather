package com.dailyweather.android.logic

import androidx.lifecycle.liveData
import com.dailyweather.android.logic.dao.PlaceDao
import com.dailyweather.android.logic.model.Place
import com.dailyweather.android.logic.model.Weather
import com.dailyweather.android.logic.network.DailyWeatherNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlin.coroutines.CoroutineContext

object Repository {

    private const val STATUS_OK = "ok"

    fun searchPlaces(placeName: String) = fire<List<Place>>(Dispatchers.IO) {
        val placeResponse = DailyWeatherNetwork.searchPlaces(placeName)
        if (placeResponse.status == STATUS_OK) {
            Result.success(placeResponse.places)
        } else {
            Result.failure(RuntimeException("response status is ${placeResponse.status}"))
        }

    }

    fun refreshWeather(lng: String, lat: String) = fire<Weather>(Dispatchers.IO) {
        coroutineScope {
            val deferredRealtimeWeather = async {
                DailyWeatherNetwork.getRealtimeWeather(lng, lat)
            }
            val deferredDailWeather =  async {
                DailyWeatherNetwork.getDailWeather(lng, lat)
            }
            val realtimeResponse = deferredRealtimeWeather.await()
            val dailyResponse = deferredDailWeather.await()
            if ((realtimeResponse.status == STATUS_OK) && (dailyResponse.status == STATUS_OK)) {
                val weather = Weather(realtimeResponse.result.realtime, dailyResponse.result.daily)
                Result.success(weather)
            } else {
                Result.failure(java.lang.RuntimeException("realtime response status is ${realtimeResponse.status} \n" +
                        "daily response status is ${dailyResponse.status}"))
            }
        }

    }

    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
        liveData<Result<T>>(context) {
            val result = try {
                block()
            } catch (e: Throwable) {
                Result.failure(e)
            }
            emit(result)
        }

    fun savePlace(place: Place) = PlaceDao.savePlace(place)

    fun getSavedPlace() = PlaceDao.getSavedPlace()

    fun isPlaceSaved() = PlaceDao.isPlaceSaved()
}