package com.ooos.sunshine.ui.weather

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.ooos.sunshine.R
import com.ooos.sunshine.logic.model.DailyResponse
import com.ooos.sunshine.logic.model.getSky
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class WeatherAdapter(private val daily: DailyResponse.Daily) :
    RecyclerView.Adapter<WeatherAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dateInfo: TextView = itemView.findViewById(R.id.dateInfo)
        val skyIcon: ImageView = itemView.findViewById(R.id.skyIcon)
        val skyInfo: TextView = itemView.findViewById(R.id.skyInfo)
        val temperatureInfo: TextView = itemView.findViewById(R.id.temperatureInfo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherAdapter.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.forecast_item, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: WeatherAdapter.ViewHolder, position: Int) {
        val skycon = daily.skycon[position]
        val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.CHINA)
        val date = dateFormat.parse(skycon.date)
        holder.dateInfo.text = SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(date)
        val sky = getSky(skycon.value)
        holder.skyIcon.setImageResource(sky.icon)
        holder.skyInfo.text = sky.info
        val temperature = daily.temperature[position]
        val tempText = "${temperature.min.toInt()} ~ ${temperature.max.toInt()} ℃"
        holder.temperatureInfo.text = tempText
    }

    override fun getItemCount(): Int {
        return daily.skycon.size
    }
}