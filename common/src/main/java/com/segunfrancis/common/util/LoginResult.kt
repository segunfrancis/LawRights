package com.segunfrancis.common.util

sealed class LoginResult<out T> {
    object Loading : LoginResult<Nothing>()
    data class Error(val error: Throwable) : LoginResult<Nothing>()
}
