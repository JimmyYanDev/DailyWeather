package com.dailyweather.android.logic.network

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object DailyWeatherNetwork {
    private val placeService: PlaceService by lazy { ServiceCreator.create<PlaceService>() }
    private val weatherService: WeatherService by lazy { ServiceCreator.create<WeatherService>() }

    suspend fun searchPlaces(placeName: String) = placeService.searchPlaces(placeName).await()

    suspend fun getDailWeather(lng: String, lat: String) = weatherService.getDailyWeather(lng, lat).await()

    suspend fun getRealtimeWeather(lng: String, lat: String) = weatherService.getRealtimeWeather(lng, lat).await()

    private suspend fun <T> Call<T>.await() = suspendCoroutine<T> {
        enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                val body = response.body()
                Log.i("TAG", "onResponse: " + body)
                if (body != null) {
                    it.resume(body)
                } else {
                    it.resumeWithException(RuntimeException("response body is null"))
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                it.resumeWithException(t)
            }

        })
    }

}