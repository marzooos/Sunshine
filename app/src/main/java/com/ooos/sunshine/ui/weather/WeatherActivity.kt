package com.ooos.sunshine.ui.weather

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ooos.sunshine.BaseActivity
import com.ooos.sunshine.databinding.ActivityWeatherBinding
import com.ooos.sunshine.logic.model.Weather
import com.ooos.sunshine.logic.model.getSky

class WeatherActivity : BaseActivity() {

    private lateinit var binding: ActivityWeatherBinding

    val viewModel by lazy {
        ViewModelProvider(this).get(WeatherViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeatherBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (viewModel.lng.isEmpty()) {
            viewModel.lng = intent.getStringExtra("lng") ?: ""
        }
        if (viewModel.lat.isEmpty()) {
            viewModel.lat = intent.getStringExtra("lat") ?: ""
        }
        if (viewModel.placeName.isEmpty()) {
            viewModel.placeName = intent.getStringExtra("place_name") ?: ""
        }
        Log.i(
            "SUNSHINE",
            "placeName = ${viewModel.placeName}, lng = ${viewModel.lng}, lat = ${viewModel.lat}"
        )
        viewModel.weatherLiveData.observe(this, { result ->
            Log.d("SUNSHINE", "WeatherLiveData has changed.")
            val weather = result.getOrNull()
            if (weather != null) {
                Log.d("SUNSHINE", "Receive weather data, update view.")
                showWeatherInfo(weather)
            } else {
                Log.d("SUNSHINE", "Weather data is null.")
                result.exceptionOrNull()?.printStackTrace()
            }
        })
        viewModel.queryWeather(viewModel.lng, viewModel.lat)
    }

    private fun showWeatherInfo(weather: Weather) {

        binding.nowLayout.placeName.text = viewModel.placeName
        val realtime = weather.realtime
        val currentTempText = "${realtime.temperature.toInt()} ℃"
        binding.nowLayout.currentTemp.text = currentTempText
        val sky = getSky(realtime.skycon)
        binding.nowLayout.currentSky.text = sky.info
        val currentPM25Text = "空气指数 ${realtime.airQuality.aqi.chn.toInt()}"
        binding.nowLayout.currentAQI.text = currentPM25Text
        binding.nowLayout.weatherNowLayout.setBackgroundResource(sky.bg)

        val daily = weather.daily
        val layoutManager = LinearLayoutManager(this)
        binding.forecastLayout.forecastRecyclerView.layoutManager = layoutManager
        val weatherAdapter = WeatherAdapter(daily)
        binding.forecastLayout.forecastRecyclerView.adapter = weatherAdapter

        val lifeIndex = daily.lifeIndex
        binding.lifeIndexLayout.coldRiskText.text = lifeIndex.coldRisk[0].desc
        binding.lifeIndexLayout.dressingText.text = lifeIndex.dressing[0].desc
        binding.lifeIndexLayout.ultravioletText.text = lifeIndex.ultraviolet[0].desc
        binding.lifeIndexLayout.carWashingText.text = lifeIndex.carWashing[0].desc
    }
}