package com.idbs.weather.db

import android.content.Context
import androidx.room.*
import com.idbs.weather.ui.cites.CitiesModel

@Database(entities =[CitiesModel.Cities::class],
    version = 1)
//@TypeConverters(Converters::class,CategoryConverters::class , ExtensionAttributesConverters::class)
abstract class WeatherDB :RoomDatabase(){
    companion object {
        //Volatile: this var is available for all other threads immediately
        @Volatile private var instance: WeatherDB?=null
        private val LOCK = Any()
        operator fun invoke(context:Context)= instance?: synchronized(LOCK){
            instance ?: buildDataBase(context).also {
                instance = it
            }
        }

        private fun buildDataBase(context: Context)=Room.databaseBuilder(
            context.applicationContext,
            WeatherDB::class.java,
            "weather.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }


    abstract fun getCityDao():CitiesDao


}