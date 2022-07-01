package com.idbs.weather.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.idbs.weather.ui.cites.CitiesModel
import kotlinx.coroutines.flow.Flow

@Dao
interface CitiesDao {

    @Query("select * from city")
    fun getAll(): Flow<List<CitiesModel.Cities>>

    @Insert
  suspend  fun insert(city:CitiesModel.Cities)

    @Delete
    fun delete(city:CitiesModel.Cities)


    @Query("DELETE FROM city")
    fun deleteAll()




}