package com.practice.andbluetooth

sealed class DataState<T>(val data:T? = null ,  val message: String? = null) {
    class Success<T>(data: T? = null) : DataState<T>(data)
    class Error<T>(message: String? = null) : DataState<T>(message = null)
    class Info<T>(message: String? = null) : DataState<T>(message = null)
    class Loading<T>() : DataState<T>()
}