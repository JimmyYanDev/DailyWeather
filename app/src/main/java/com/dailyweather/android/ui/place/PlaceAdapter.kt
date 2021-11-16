package com.dailyweather.android.ui.place

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dailyweather.android.R
import com.dailyweather.android.logic.model.Place

class PlaceAdapter(private val placeFragment: PlaceFragment, private val places: List<Place>) : RecyclerView.Adapter<PlaceAdapter.ViewHolder>(){

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val placeName = itemView.findViewById<TextView>(R.id.placeName)
        val placeAddress = itemView.findViewById<TextView>(R.id.placeAddress)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.adapter_place_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val place = places[position]
        holder.placeAddress.text = place.address
        holder.placeName.text = place.name
    }

    override fun getItemCount() = places.size
}