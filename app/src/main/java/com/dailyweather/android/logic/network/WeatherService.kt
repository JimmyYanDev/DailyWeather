package com.dailyweather.android.logic.network

import com.dailyweather.android.App
import com.dailyweather.android.logic.model.DailyResponse
import com.dailyweather.android.logic.model.RealtimeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface WeatherService {

    @GET("v2.5/${App.TOKEN}/{lat},{lng}/realtime.json")
    fun getRealtimeWeather(
        @Path("lng") lng: String,
        @Path("lat") lat: String
    ): Call<RealtimeResponse>

    @GET("v2.5/${App.TOKEN}/{lat},{lng}/daily.json")
    fun getDailyWeather(
        @Path("lng") lng: String,
        @Path("lat") lat: String
    ): Call<DailyResponse>
}