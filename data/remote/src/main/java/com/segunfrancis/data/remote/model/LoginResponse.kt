package com.segunfrancis.data.remote.model

data class LoginResponse(
    val access_token: String,
    val expires_in: Long,
    val token_type: String
)