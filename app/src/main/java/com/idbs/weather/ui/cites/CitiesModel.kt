package com.idbs.weather.ui.cites
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CitiesModel(
    val cities: List<Cities> = listOf()
    ): Parcelable{
    @Parcelize
    data class Cities(var id:Int = 0,var name: String? = ""):Parcelable
    }
