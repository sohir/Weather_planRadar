package com.idbs.weather.ui.cites
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

data class CitiesModel(
    val cities: List<Cities> = listOf()
    ){
    @Entity(tableName = "city")
    data class Cities(
        @PrimaryKey(autoGenerate = true)
        var id:Int = 0,
        var name: String = "")
    }
