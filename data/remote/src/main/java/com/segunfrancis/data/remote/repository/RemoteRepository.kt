package com.segunfrancis.data.remote.repository

import com.segunfrancis.data.remote.model.LoginResponse

interface RemoteRepository {

    fun addToken(token: String)
    fun getToken(): String
    fun addLastUpdated(time: Long)
    fun getLastUpdated(): Long
    suspend fun login(): LoginResponse
}
