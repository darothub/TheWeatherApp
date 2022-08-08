package com.darothub.theweatherapp.com.darothub.theweatherapp.weather.ui

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.darothub.theweatherapp.R
import com.darothub.theweatherapp.com.darothub.theweatherapp.domain.model.Hour
import com.darothub.theweatherapp.core.utils.convertEpochTimeToStringFormat
import com.darothub.theweatherapp.core.utils.convertTempToScientificReading
import com.darothub.theweatherapp.core.utils.hide
import com.darothub.theweatherapp.databinding.CurrentWeatherLayoutBinding


class DataViewHolder(private val binding: CurrentWeatherLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bindTo(hour: Hour) {
        val context = binding.root.context
        binding.apply {
            welcomeTv.hide()
            temp.textSize = 14f
            val s = convertTempToScientificReading(hour.tempC)
            updateDateTv.text = s
            temp.text = convertEpochTimeToStringFormat(hour.timeEpoch)
            description.text = hour.condition.text
            wind.text = context.getString(R.string.wind, hour.windMph.toString())
            pressure.text = context.getString(R.string.wind, hour.pressureIn.toString())
            humidity.text = context.getString(R.string.wind, hour.humidity.toString())
            sunrise.visibility = View.GONE
            sunset.visibility = View.GONE
            val icon = "https:" + hour.condition.icon
            weatherImage.load(icon)
            weatherImage.setColorFilter(ContextCompat.getColor(weatherImage.context, R.color.black), android.graphics.PorterDuff.Mode.MULTIPLY)
        }
    }
}
