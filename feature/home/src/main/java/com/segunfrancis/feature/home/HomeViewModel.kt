package com.segunfrancis.feature.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.segunfrancis.common.util.LoginResult
import com.segunfrancis.data.remote.repository.RemoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class HomeViewModel @Inject constructor(private val remoteRepository: RemoteRepository) :
    ViewModel() {

    private val _token = MutableLiveData<LoginResult<String>>()
    val token: LiveData<LoginResult<String>> get() = _token

    private val exceptionHandler: CoroutineExceptionHandler =
        CoroutineExceptionHandler { _, throwable ->
            _token.postValue(LoginResult.Error(throwable))
            Timber.e(throwable)
        }

    init {
        _token.postValue(LoginResult.Loading)
        viewModelScope.launch(exceptionHandler) {
            val loginResponse = remoteRepository.login()
            Timber.d("LoginResponse: $loginResponse")
            remoteRepository.addToken("Bearer ".plus(loginResponse.access_token)).collect {
                Timber.d("Token updated")
            }
        }
    }
}
