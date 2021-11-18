package com.dailyweather.android.ui.place

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dailyweather.android.R
import com.dailyweather.android.logic.model.Place
import com.dailyweather.android.ui.weather.WeatherActivity
import kotlinx.android.synthetic.main.activity_weather.*

class PlaceAdapter(private val placeFragment: PlaceFragment, private val places: List<Place>) : RecyclerView.Adapter<PlaceAdapter.ViewHolder>(){

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val placeName = itemView.findViewById<TextView>(R.id.placeName)
        val placeAddress = itemView.findViewById<TextView>(R.id.placeAddress)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.adapter_place_item, parent, false)
        val holder = ViewHolder(itemView)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val place = places[position]
        holder.placeAddress.text = place.address
        holder.placeName.text = place.name
        holder.itemView.setOnClickListener {
            val activity = placeFragment.activity
            if (activity is WeatherActivity) {
                activity.drawerLayout.closeDrawers()
                activity.viewModel.locationLng = place.location.lng
                activity.viewModel.locationLat = place.location.lat
                activity.viewModel.placeName = place.name
                activity.refreshWeather()
            } else {
                val intent = Intent(placeFragment.context, WeatherActivity::class.java).apply {
                    putExtra(WeatherActivity.EXTRA_LOCATION_LNG, place.location.lng)
                    putExtra(WeatherActivity.EXTRA_LOCATION_LAT, place.location.lat)
                    putExtra(WeatherActivity.EXTRA_PLACE_NAME, place.name)
                }
                placeFragment.startActivity(intent)
                activity?.finish()
            }
            placeFragment.viewModel.savePlace(place)
        }
    }

    override fun getItemCount() = places.size
}