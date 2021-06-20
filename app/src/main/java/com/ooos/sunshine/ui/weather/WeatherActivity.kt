package com.ooos.sunshine.ui.weather

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ooos.sunshine.BaseActivity
import com.ooos.sunshine.R
import com.ooos.sunshine.databinding.ActivityWeatherBinding
import com.ooos.sunshine.logic.model.Weather
import com.ooos.sunshine.logic.model.getSky

class WeatherActivity : BaseActivity() {

    lateinit var binding: ActivityWeatherBinding

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
        viewModel.weatherLiveData.observe(this, { result ->
            val weather = result.getOrNull()
            if (weather != null) {
                showWeatherInfo(weather)
            } else {
                result.exceptionOrNull()?.printStackTrace()
            }
            binding.swipeRefresh.isRefreshing = false
        })
        binding.swipeRefresh.setColorSchemeResources(R.color.purple_200)
        binding.swipeRefresh.setOnRefreshListener {
            refreshWeather()
        }
        refreshWeather()
        // Swipe menu.
        binding.nowLayout.navBtn.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }
        binding.drawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {}

            override fun onDrawerOpened(drawerView: View) {}

            override fun onDrawerClosed(drawerView: View) {
                // Hide keyboard.
                val manager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                manager.hideSoftInputFromWindow(
                    drawerView.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS
                )
            }

            override fun onDrawerStateChanged(newState: Int) {}

        })
    }

    fun refreshWeather() {
        viewModel.queryWeather(viewModel.lng, viewModel.lat)
        binding.swipeRefresh.isRefreshing = true
    }

    private fun showWeatherInfo(weather: Weather) {
        // Interface module 1.
        binding.nowLayout.placeName.text = viewModel.placeName
        val realtime = weather.realtime
        val currentTempText = "${realtime.temperature.toInt()} ℃"
        binding.nowLayout.currentTemp.text = currentTempText
        val sky = getSky(realtime.skycon)
        binding.nowLayout.currentSky.text = sky.info
        val currentPM25Text = "空气指数 ${realtime.airQuality.aqi.chn.toInt()}"
        binding.nowLayout.currentAQI.text = currentPM25Text
        binding.nowLayout.weatherNowLayout.setBackgroundResource(sky.bg)
        // Interface module 2.
        val daily = weather.daily
        val layoutManager = LinearLayoutManager(this)
        binding.forecastLayout.forecastRecyclerView.layoutManager = layoutManager
        val weatherAdapter = WeatherAdapter(daily)
        binding.forecastLayout.forecastRecyclerView.adapter = weatherAdapter
        // Interface module 3.
        val lifeIndex = daily.lifeIndex
        binding.lifeIndexLayout.coldRiskText.text = lifeIndex.coldRisk[0].desc
        binding.lifeIndexLayout.dressingText.text = lifeIndex.dressing[0].desc
        binding.lifeIndexLayout.ultravioletText.text = lifeIndex.ultraviolet[0].desc
        binding.lifeIndexLayout.carWashingText.text = lifeIndex.carWashing[0].desc
    }
}