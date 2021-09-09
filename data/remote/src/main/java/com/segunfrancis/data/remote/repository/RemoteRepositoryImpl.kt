package com.segunfrancis.data.remote.repository

import android.content.SharedPreferences
import com.segunfrancis.data.remote.api.LawRightsApi
import com.segunfrancis.data.remote.model.LoginRequest
import com.segunfrancis.data.remote.model.LoginResponse
import com.segunfrancis.data.remote.util.RemoteConstants.TOKEN_PREF_KEY
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class RemoteRepositoryImpl @Inject constructor(
    private val api: LawRightsApi,
    private val preferences: SharedPreferences,
    private val dispatcher: CoroutineDispatcher
) : RemoteRepository {
    override fun addToken(token: String): Flow<Unit> {
        return flow { emit(preferences.edit().putString(TOKEN_PREF_KEY, token).apply()) }
    }

    override suspend fun login(): LoginResponse {
        return withContext(dispatcher) {
            api.login(LoginRequest())
        }
    }
}
