package com.segunfrancis.data.remote.repository

import com.segunfrancis.data.remote.model.LoginResponse
import kotlinx.coroutines.flow.Flow

interface RemoteRepository {

    fun addToken(token: String): Flow<Unit>
    suspend fun login(): LoginResponse
}
