package com.dailyweather.android.ui.place

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.dailyweather.android.MainActivity
import com.dailyweather.android.R
import com.dailyweather.android.ui.weather.WeatherActivity
import kotlinx.android.synthetic.main.fragment_place.*

class PlaceFragment : Fragment() {

    internal val viewModel by lazy {
        ViewModelProviders.of(this).get(PlaceViewModel::class.java)
    }

    private lateinit var adapter: PlaceAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_place, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (activity is MainActivity && viewModel.isPlaceSaved()) {
            val place = viewModel.getSavedPlace()
            val intent = Intent(context, WeatherActivity::class.java).apply {
                putExtra(WeatherActivity.EXTRA_LOCATION_LNG, place.location.lng)
                putExtra(WeatherActivity.EXTRA_LOCATION_LAT, place.location.lat)
                putExtra(WeatherActivity.EXTRA_PLACE_NAME, place.name)
            }
            startActivity(intent)
            activity?.finish()
            return
        }

        adapter = PlaceAdapter(this, viewModel.places)
        val linearLayoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = linearLayoutManager

        searchPlaceEdit.addTextChangedListener{
            val content = it.toString()
            if (content.isNotEmpty()) {
                viewModel.searchPlaces(content)
            } else {
                recyclerView.visibility = View.GONE
                bgImageView.visibility = View.VISIBLE
                viewModel.places.clear()
                adapter.notifyDataSetChanged()
            }
        }

        viewModel.placeLiveData.observe(this) {
            val result = it.getOrNull()
            if (result != null) {
                viewModel.places.clear()
                viewModel.places.addAll(result)
                recyclerView.visibility = View.VISIBLE
                bgImageView.visibility = View.GONE
                recyclerView.smoothScrollToPosition(0)
                adapter.notifyDataSetChanged()
            } else {
                recyclerView.visibility = View.GONE
                bgImageView.visibility = View.VISIBLE
                viewModel.places.clear()
                adapter.notifyDataSetChanged()
                Toast.makeText(context, "未能查询到任何地点", Toast.LENGTH_SHORT).show()
                it.exceptionOrNull()?.printStackTrace()
            }
        }
    }
}