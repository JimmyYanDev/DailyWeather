package com.dailyweather.android.logic.dao

import android.content.Context
import androidx.core.content.edit
import com.dailyweather.android.App
import com.dailyweather.android.logic.model.Place
import com.google.gson.Gson

object PlaceDao {

    const val SHARED_PREFERENCE_PLACE = "place"
    const val SHARED_PREFERENCE_NAME = "daily_weather"

    fun savePlace(place: Place) {
        sharedPreferences().edit {
            putString(SHARED_PREFERENCE_PLACE, Gson().toJson(place))
        }
    }

    fun getSavedPlace() : Place {
        val placeJson = sharedPreferences().getString(SHARED_PREFERENCE_PLACE, "")
        return Gson().fromJson(placeJson, Place::class.java)
    }

    fun isPlaceSaved() = sharedPreferences().contains(SHARED_PREFERENCE_PLACE)

    private fun sharedPreferences() =
        App.context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
}