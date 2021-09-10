package com.segunfrancis.common.util

sealed class NetworkResult<out T> {
    object Loading : NetworkResult<Nothing>()
    data class Error(val error: Throwable) : NetworkResult<Nothing>()
    data class Success<T>(val data: T) : NetworkResult<T>()
}