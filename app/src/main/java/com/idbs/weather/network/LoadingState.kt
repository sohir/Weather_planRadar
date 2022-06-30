package com.idbs.weather.network



data class LoadingState  constructor(val status: Status, val msg:String?=null,val errorResponse : ErrorResponse?=null) {
    companion object {
        val LOADED = LoadingState(Status.SUCCESS)
        val LOADING = LoadingState(Status.RUNNING)
        fun error(msg: String?,errorResponse : ErrorResponse?) = LoadingState(Status.FAILED,msg,errorResponse)
    }

    enum class Status {
        RUNNING,
        SUCCESS,
        FAILED
    }
}

