package com.idbs.weather.base

import android.util.Log
import com.idbs.weather.network.ErrorResponse
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

class NetworkState constructor(val status: Status, val msg: Any? = null, val data: Any? = null) {

    enum class Status {
        RUNNING, FAILED, SUCCESS
    }

    companion object {
        fun getLoaded(dataSuccess: Any?): NetworkState {
            return NetworkState(Status.SUCCESS, data = dataSuccess)
        }

        var LOADING: NetworkState = NetworkState(Status.RUNNING)
        fun getErrorMessage(throwable: Throwable): NetworkState {
            return when (throwable) {
                is IOException -> {
                    NetworkState(Status.FAILED, "No Connection ")
                }
                is SocketTimeoutException -> {
                    NetworkState(Status.FAILED, "Bad Connection")
                }
                is HttpException -> {
                    val code = throwable.code()
                    val message = throwable.localizedMessage
                    val error = JsoneErrorParsing(throwable)
                    if (error?.message ?: "" != "")
                        NetworkState(Status.FAILED, error?.message, error)
                    else
                        NetworkState(Status.FAILED, message)
                }
                else -> {
                    NetworkState(Status.FAILED, throwable.message)
                }
            }
        }
    }

}
fun JsoneErrorParsing (exception: HttpException): ErrorResponse?{
    var responseError : ErrorResponse? = ErrorResponse(0,"")
    try {
        exception.response()?.errorBody()?.source()?.let {
            val moshi: Moshi = Moshi.Builder().build()
            val adapter: JsonAdapter<ErrorResponse> = moshi.adapter(ErrorResponse::class.java)
            val response = adapter.fromJson(it)
            responseError = response
        }
    } catch (exception: java.lang.Exception)
    {
        Log.v("error", exception.toString())
        responseError= ErrorResponse(0,"")
    }
    return responseError
}