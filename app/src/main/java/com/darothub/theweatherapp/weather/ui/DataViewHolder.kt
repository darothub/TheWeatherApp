package com.darothub.theweatherapp.com.darothub.theweatherapp.weather.ui

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.darothub.theweatherapp.R
import com.darothub.theweatherapp.com.darothub.theweatherapp.core.entities.Hour
import com.darothub.theweatherapp.com.darothub.theweatherapp.core.entities.HourEntity
import com.darothub.theweatherapp.core.utils.convertEpochTimeToStringFormat
import com.darothub.theweatherapp.core.utils.convertTempToScientificReading
import com.darothub.theweatherapp.core.utils.hide
import com.darothub.theweatherapp.databinding.CurrentWeatherLayoutBinding


class DataViewHolder(private val binding: CurrentWeatherLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bindTo(hour: Hour) {
        binding.apply {
            welcomeTv.hide()
            temp.textSize = 14f
            val s = convertTempToScientificReading(hour.tempC)
            updateDateTv.text = s
            temp.text = convertEpochTimeToStringFormat(hour.timeEpoch)
            description.text = hour.condition.text
            wind.text = "Wind: ${hour.windMph}m/s"
            pressure.text = "Pressure: ${hour.pressureIn}hPa"
            humidity.text = "Humidity: ${hour.humidity}%"
            sunrise.visibility = View.GONE
            sunset.visibility = View.GONE
            val icon = "https:" + hour.condition.icon
            weatherImage.load(icon)
            weatherImage.setColorFilter(ContextCompat.getColor(weatherImage.context, R.color.black), android.graphics.PorterDuff.Mode.MULTIPLY)
        }
    }
}
