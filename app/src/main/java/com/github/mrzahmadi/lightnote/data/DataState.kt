package com.github.mrzahmadi.lightnote.data

sealed class DataState<T> {
    data class Loading<T>(val isLoading: Boolean) : DataState<T>()
    data class Success<T>(val data: T) : DataState<T>()
    data class Error<T>(val error: Throwable) : DataState<T>()
}