package com.segunfrancis.data.remote.repository

import android.content.SharedPreferences
import com.segunfrancis.data.remote.api.LawRightsApi
import com.segunfrancis.data.remote.model.LoginRequest
import com.segunfrancis.data.remote.model.LoginResponse
import com.segunfrancis.data.remote.util.RemoteConstants.LAST_UPDATED_KEY
import com.segunfrancis.data.remote.util.RemoteConstants.TOKEN_PREF_KEY
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class RemoteRepositoryImpl @Inject constructor(
    private val api: LawRightsApi,
    private val preferences: SharedPreferences,
    private val dispatcher: CoroutineDispatcher
) : RemoteRepository {
    override fun addToken(token: String) {
        return preferences.edit().putString(TOKEN_PREF_KEY, token).apply()
    }

    override fun getToken(): String {
        return preferences.getString(TOKEN_PREF_KEY, null) ?: ""
    }

    override fun addLastUpdated(time: Long) {
        preferences.edit().putLong(LAST_UPDATED_KEY, time).apply()
    }

    override fun getLastUpdated(): Long {
        return preferences.getLong(LAST_UPDATED_KEY, 0)
    }

    override suspend fun login(): LoginResponse {
        return withContext(dispatcher) {
            api.login(LoginRequest())
        }
    }
}
