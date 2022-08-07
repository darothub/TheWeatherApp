package com.darothub.theweatherapp.core.utils

import android.app.Activity
import android.content.Context
import android.location.Geocoder
import android.location.Location
import android.text.SpannableString
import android.text.Spanned
import android.text.format.DateFormat
import android.text.style.SuperscriptSpan
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.darothub.theweatherapp.R
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*



fun convertLongToTime(longTime: Long): String {
    val dt = Date(longTime)
    val sdf = SimpleDateFormat("hh:mm a", Locale.US)
    return sdf.format(dt)
}
fun convertEpochTimeToStringFormat(ts: Long?): String {
    if (ts == null) return ""
    // Get instance of calendar
    val calendar = Calendar.getInstance(Locale.getDefault())
    // get current date from ts
    calendar.timeInMillis = Date(ts * 1000).time
    // return formatted date
    return DateFormat.format("E dd.MM.yyyy hh:mm", calendar).toString()
}

fun setTextsColorToWhite(textView: TextView) {
    textView.setTextColor(ContextCompat.getColor(textView.context, R.color.white))
}

fun setTextViewsColor(color: Int, vararg textView: TextView) {
    for (tv in textView) {
        tv.setTextColor(ContextCompat.getColor(tv.context, color))
    }
}

fun View.show() {
    visibility = View.VISIBLE
}
fun View.hide() {
    visibility = View.GONE
}

fun Double.roundOff(): String {
    val df = DecimalFormat("#.##")
    df.roundingMode = RoundingMode.DOWN
   return df.format(this)
}
fun convertTempToScientificReading(tempInCelsius: Double): SpannableString {
    var str = "$tempInCelsius" + "oC"
    val indexOfO = str.indexOf('o')
    val s = SpannableString(str)
    s.setSpan(
        SuperscriptSpan(),
        indexOfO,
        str.length - 1,
        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    return s
}
