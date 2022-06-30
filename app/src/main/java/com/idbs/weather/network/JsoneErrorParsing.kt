package com.idbs.weather.network

import android.util.Log
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import retrofit2.HttpException
import java.lang.Exception

 fun JsoneErrorParsing (exception: HttpException):ErrorResponse?{
    var responseError :ErrorResponse? = ErrorResponse(0,"")
    try {
        exception.response()?.errorBody()?.source()?.let {
            val moshi: Moshi = Moshi.Builder().build()
            val adapter: JsonAdapter<ErrorResponse> = moshi.adapter(ErrorResponse::class.java)
            val response = adapter.fromJson(it)
            responseError = response
        }
    } catch (exception: Exception)
    {
        Log.v("error", exception.toString())
        responseError=ErrorResponse(0,"")
    }
 return responseError
}