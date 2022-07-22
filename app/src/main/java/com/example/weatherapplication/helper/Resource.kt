package com.example.weatherapplication.helper

data class Resource<out T>(val status: Status, val data: T?, val message: String?) {
    enum class Status {
        LOADING,
        SUCCESS,
        ERROR,
    }

    companion object {
        fun <T> Success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null )
        }

        fun <T> Error(msg: String?, data: T?=null): Resource<T> {
            return Resource(Status.ERROR, data, msg )
        }

        fun <T> Loading(): Resource<T> {
            return Resource(Status.LOADING,null,null)
        }
    }

}
