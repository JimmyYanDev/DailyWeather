package com.dailyweather.android.logic

import android.util.Log
import androidx.lifecycle.liveData
import com.dailyweather.android.logic.model.Place
import com.dailyweather.android.logic.network.DailyWeatherNetwork
import kotlinx.coroutines.Dispatchers
import java.lang.RuntimeException

object Repository {

    fun searchPlaces(placeName: String) = liveData<Result<List<Place>>>(Dispatchers.IO) {
        val result = try {
            val placeResponse = DailyWeatherNetwork.searchPlaces(placeName)
            Log.i("TAG", "placeResponse: ${placeResponse.places}")
            if (placeResponse.status == "ok") {
                Result.success(placeResponse.places)
            } else {
                Result.failure(RuntimeException("response status is ${placeResponse.status}"))
            }
        } catch (e: Throwable) {
            Log.i("TAG", "searchPlaces: ")
            Result.failure(e)
        }
        emit(result)
    }

}