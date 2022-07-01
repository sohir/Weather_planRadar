package com.idbs.weather.utilit

import android.util.Log
import java.math.RoundingMode
import java.text.DecimalFormat
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun convertToCelsius(kelvinTemp:Double):String{
    var convertResult = (kelvinTemp - 273.15)
    val df = DecimalFormat("#")
    df.roundingMode = RoundingMode.CEILING
    convertResult =  df.format(convertResult).toDouble()
    Log.v("temp","$kelvinTemp :  ${(kelvinTemp - 273.15)}")
    return "$convertResult C"
}

fun showDate(dateLong:Long):String{
    val dt = Instant.ofEpochSecond(dateLong)
        .atZone(ZoneId.systemDefault())
        .toLocalDateTime()

    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy - HH:mm")
    val output = formatter.format(dt)

    //   Log.d("parseTesting", date.month.toString()) // prints August
    Log.v("time"," is: ${output}")
    return output
}